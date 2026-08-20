"""Generate complete Japanese utterances used by the vocabulary/grammar module.

The output name is content-addressed and matches AudioPlayer.speakJapanese().
Run in CI so every APK contains the same offline voice files.
"""

from __future__ import annotations

import asyncio
import hashlib
import re
from pathlib import Path

import edge_tts


ROOT = Path(__file__).resolve().parents[1]
SOURCE = ROOT / "app/src/main/java/com/studyn5/kana/data/LanguageLearningData.kt"
OUTPUT = ROOT / "app/src/main/assets/audio"
VOICE = "ja-JP-NanamiNeural"
RATE = "-8%"


def japanese_number(number: int) -> str:
    kanji = ["", "一", "二", "三", "四", "五", "六", "七", "八", "九"]
    if number == 100:
        return "百"
    tens, ones = divmod(number, 10)
    return ("" if tens == 0 else "十" if tens == 1 else kanji[tens] + "十") + kanji[ones]


def hiragana_number(number: int) -> str:
    reading = ["", "いち", "に", "さん", "よん", "ご", "ろく", "なな", "はち", "きゅう"]
    if number == 100:
        return "ひゃく"
    tens, ones = divmod(number, 10)
    return ("" if tens == 0 else "じゅう" if tens == 1 else reading[tens] + "じゅう") + reading[ones]


def collect_texts() -> list[str]:
    source = SOURCE.read_text(encoding="utf-8")
    quoted = re.findall(r'"((?:[^"\\]|\\.)*)"', source)
    texts = {
        value.replace(r'\"', '"')
        for value in quoted
        if re.search(r"[ぁ-んァ-ヶ一-龯]", value) and "$" not in value and "↔" not in value
    }

    for number in range(1, 101):
        kanji = japanese_number(number)
        hiragana = hiragana_number(number)
        texts.add(kanji)
        texts.add(hiragana)
        texts.add(f"ばんごうは{hiragana}です。")

    countries = ["ベトナム", "にほん", "かんこく", "ちゅうごく", "アメリカ", "イギリス", "イタリア", "フランス", "ドイツ", "タイ"]
    nationalities = ["ベトナムじん", "にほんじん", "かんこくじん", "ちゅうごくじん", "アメリカじん", "イギリスじん", "イタリアじん", "フランスじん", "ドイツじん", "タイじん"]
    languages = ["ベトナムご", "にほんご", "かんこくご", "ちゅうごくご", "えいご", "イタリアご", "フランスご", "ドイツご", "タイご"]
    for value in countries:
        texts.update((value, f"{value}からです。"))
    for value in nationalities:
        texts.update((value, f"わたしは{value}です。"))
    for value in languages:
        texts.update((value, f"{value}ができます。", f"{value}ができますか。"))

    names = ["マイ", "ティン", "キム", "カーラ", "アン"]
    jobs = ["ソフトウェアエンジニア", "かいはつしゃ", "きょうし", "がくせい", "かいしゃいん", "いしゃ", "エンジニア", "ぎんこういん"]
    family = ["ちち", "はは", "あに", "あね", "おとうと"]
    for name in names:
        texts.add(f"はじめまして。わたしは{name}です。")
    for job in jobs:
        texts.add(f"{job}です。")
    for member in family:
        texts.add(f"わたしの{member}です。")

    def age_text(age: int) -> str:
        return "はたち" if age == 20 else f"{japanese_number(age)}さい"

    for age in range(20, 50):
        texts.add(f"{age_text(age)}です。")
    for people in range(3, 9):
        texts.add(f"{japanese_number(people)}にんです。")

    # Full-dialogue buttons speak one prepared utterance, not stitched syllables.
    dialogue_lines = re.findall(r'e\("([^"$]+[。！？])",\s*"', source)
    texts.update(dialogue_lines)

    # Exact whole-dialogue audio used by the "Nghe toàn đoạn" action.
    for index in range(25):
        name = names[index % len(names)]
        other_name = names[(index // len(names) + 1) % len(names)]
        texts.add(" ".join((
            f"はじめまして。わたしは{name}です。",
            f"はじめまして。わたしは{other_name}です。",
            "どうぞよろしくおねがいします。",
        )))
        country = countries[index % len(countries)]
        language = languages[index % len(languages)]
        job = jobs[index % 5]
        texts.add(f"はじめまして。{other_name}です。")
        texts.add(" ".join((f"はじめまして。{other_name}です。", "どちらからですか。", f"{country}からです。", "おしごとはなんですか。", f"{job}です。", f"{language}ができますか。")))
        age = 20 + index
        people = 3 + index % 6
        texts.add(" ".join(("なんさいですか。", f"{age_text(age)}です。", "かぞくはなんにんですか。", f"{japanese_number(people)}にんです。")))
        member = family[index % len(family)]
        family_job = ["いしゃ", "きょうし", "エンジニア", "かいしゃいん", "ぎんこういん"][index % 5]
        family_age = 25 + index
        texts.add(" ".join(("このひとはだれですか。", f"わたしの{member}です。", "おしごとはなんですか。", f"{family_job}です。", f"{age_text(family_age)}です。")))
    return sorted(texts)


def output_path(text: str) -> Path:
    digest = hashlib.sha256(text.encode("utf-8")).hexdigest()[:16]
    return OUTPUT / f"language_{digest}.mp3"


async def generate_one(text: str, semaphore: asyncio.Semaphore) -> None:
    target = output_path(text)
    if target.exists() and target.stat().st_size > 1_000:
        return
    async with semaphore:
        last_error: Exception | None = None
        for attempt in range(3):
            try:
                await edge_tts.Communicate(text=text, voice=VOICE, rate=RATE).save(str(target))
                if target.stat().st_size <= 1_000:
                    raise RuntimeError(f"Audio too small: {target}")
                return
            except Exception as error:  # transient service/network failure
                last_error = error
                target.unlink(missing_ok=True)
                await asyncio.sleep(1.5 * (attempt + 1))
        raise RuntimeError(f"Cannot generate audio for {text!r}") from last_error


async def main() -> None:
    OUTPUT.mkdir(parents=True, exist_ok=True)
    texts = collect_texts()
    print(f"Generating/verifying {len(texts)} Japanese utterances with {VOICE}")
    semaphore = asyncio.Semaphore(6)
    await asyncio.gather(*(generate_one(text, semaphore) for text in texts))


if __name__ == "__main__":
    asyncio.run(main())
