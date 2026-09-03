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
SOURCES = (
    ROOT / "app/src/main/java/com/studyn5/kana/data/LanguageLearningData.kt",
    ROOT / "app/src/main/java/com/studyn5/kana/data/KanjiData.kt",
)
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
    source = "\n".join(path.read_text(encoding="utf-8") for path in SOURCES)
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
    country_languages = ["ベトナムご", "にほんご", "かんこくご", "ちゅうごくご", "えいご", "えいご", "イタリアご", "フランスご", "ドイツご", "タイご"]
    for value in countries:
        texts.update((value, f"{value}からです。"))
    for value in nationalities:
        texts.update((value, f"わたしは{value}です。"))
    for value in languages:
        texts.update((value, f"{value}ができます。", f"{value}ができますか。"))

    names = ["マイ", "ティン", "キム", "カーラ", "アン"]
    jobs = ["ソフトウェアエンジニア", "かいはつしゃ", "きょうし", "がくせい", "かいしゃいん", "ぎんこういん", "いしゃ", "こうむいん", "しゅふ"]
    cities = ["ホーチミン", "とうきょう", "ソウル", "ペキン", "ニューヨーク", "ロンドン", "ローマ", "パリ", "ベルリン", "バンコク"]
    family = ["ちち", "はは", "あに", "あね", "おとうと"]
    for name in names:
        texts.update((
            f"はじめまして。わたしは{name}です。",
            f"はじめまして。{name}です。",
            f"{name}です。",
            f"わたしは{name}です。おなまえはなんですか。",
            f"はい、げんきです。{name}さんは。",
            f"すみません、{name}さんですか。",
            f"いいえ、{name}です。",
            f"すみません。わたしは{name}です。",
            f"はい、{name}です。よろしくおねがいします。",
            f"はじめまして。{name}です。どちらからですか。",
            f"はい、そうです。{name}さんは。",
        ))
    for job in jobs:
        texts.update((f"{job}です。", f"{job}です。あなたは。", f"{job}です。どちらからですか。"))
        for other_job in jobs:
            texts.add(f"{job}です。おかあさんは{other_job}です。")
    for member in family:
        texts.update((f"わたしの{member}です。", f"はい、{member}がいます。"))
    for city in cities:
        texts.update((f"{city}です。", f"{city}にすんでいます。", f"{city}にいます。"))

    foods = ["すし", "やきにく", "カレー", "ラーメン", "ぎょうざ", "てんぷら", "うどん", "ピザ", "さかな", "やさい"]
    drinks = ["コーヒー", "おちゃ", "こうちゃ", "ぎゅうにゅう", "ジュース", "みず"]
    breakfasts = ["パンとたまご", "ごはんとみそしる", "くだものとぎゅうにゅう", "おにぎりとおちゃ", "サンドイッチとコーヒー"]
    for food in foods:
        texts.update((
            f"{food}がだいすきです。", f"{food}もすきですか。",
            f"いいえ、{food}はあまりすきじゃないです。",
            f"{food}をおすすめします。", f"{food}はどんなたべものですか。",
            f"では、{food}をおねがいします。", f"{food}をたべます。",
            f"あのみせで{food}をたべます。",
        ))
        for name in names:
            texts.add(f"{food}がすきです。{name}さんは。")
    for drink in drinks:
        texts.update((
            f"{drink}、のみますか。", f"では、{drink}はどうですか。",
            f"{drink}をのみます。", f"{drink}をおねがいします。",
        ))
    for breakfast in breakfasts:
        texts.add(f"{breakfast}です。")

    lesson6_foods = ["カレー", "すし", "チーズバーガー", "ホットドッグ", "フライドポテト", "ラーメン", "うどん", "ピザ"]
    lesson6_drinks = ["コーラ", "オレンジジュース", "コーヒー", "ウーロンちゃ", "みず"]
    lesson6_places = ["レストラン", "フードコート", "しょくどう", "あのみせ", "このみせ"]
    for food in lesson6_foods:
        texts.update((
            f"{food}がいちばんすきです。", f"そうですか。わたしも{food}がすきです。",
            f"{food}をたべます。", f"{food}もたべますか。", f"{food}をたべましょう。",
        ))
        for other_food in lesson6_foods:
            texts.add(f"{food}と{other_food}がすきです。")
    for drink in lesson6_drinks:
        texts.update((f"{drink}をおねがいします。", f"{drink}をのみます。"))
    for place in lesson6_places:
        texts.update((
            f"{place}でたべませんか。", f"{place}はどうですか。",
            f"{place}もいいですよ。", f"{place}はおいしいですか。",
            f"じゃ、{place}でたべましょう。", f"{place}でたべましょう。",
        ))
        for food in lesson6_foods:
            texts.update((
                f"{food}ですか。", f"はい、{place}の{food}はおいしいですよ。",
                f"{place}はやすいですか。",
                f"はい、やすいです。でも、{food}はおいしくないです。",
            ))
    # Reproduce the 25 generated Lesson 6 dialogues exactly. This avoids the
    # online TTS fallback when a user plays either one line or the full scene.
    lesson6_quantities = ["ひとつ", "ふたつ", "みっつ", "よっつ", "いつつ"]
    lesson6_prices = ["にひゃくはちじゅう", "さんびゃく", "よんひゃく", "ごひゃく", "ろっぴゃく"]
    for index in range(25):
        scene, variant = index % 5, index // 5
        food = lesson6_foods[(scene + variant) % len(lesson6_foods)]
        drink = lesson6_drinks[(scene + variant) % len(lesson6_drinks)]
        place = lesson6_places[(scene + variant) % len(lesson6_places)]
        other_place = lesson6_places[(scene + variant + 2) % len(lesson6_places)]
        if scene == 1:
            texts.add(f"{food}を{lesson6_quantities[variant]}と{drink}をひとつください。")
            texts.add(f"ぜんぶで{lesson6_prices[variant]}えんです。")
        elif scene == 2:
            texts.update((
                f"{place}でたべましょう。", f"{food}ですか。",
                f"はい、{place}の{food}はおいしいですよ。",
            ))
        elif scene == 4:
            texts.update((
                f"{place}はやすいですか。",
                f"はい、やすいです。でも、{food}はおいしくないです。",
                f"{other_place}はどうですか。", f"じゃ、{other_place}でたべましょう。",
            ))

    for country, nationality, language in zip(countries, nationalities, country_languages):
        texts.update((
            f"{country}からです。",
            f"わたしは{nationality}です。",
            f"いいえ、にほんじんじゃないです。{nationality}です。",
            f"{language}ができます。",
            f"{language}ができますか。",
            f"いいえ、{language}はできません。",
            f"はい、{language}ができます。",
            f"{language}がわかりますか。",
            f"{language}ができます。あなたは。",
            f"{language}とえいごができます。",
            f"{country}からです。{language}とえいごができます。",
        ))

    for name in names:
        for country, nationality in zip(countries, nationalities):
            texts.add(f"{name}さんは{nationality}ですか。")
        for language in languages:
            texts.add(f"{language}ができます。{name}さんは。")
        for job in jobs:
            texts.add(f"{job}です。{name}さんは。")

    def age_text(age: int) -> str:
        if age == 20:
            return "はたち"
        kana = hiragana_number(age)
        if age == 100:
            return "ひゃくさい"
        if age % 10 == 1:
            return f"{kana.removesuffix('いち')}いっさい"
        if age % 10 == 8:
            return f"{kana.removesuffix('はち')}はっさい"
        if age % 10 == 0:
            return f"{kana.removesuffix('じゅう')}じゅっさい"
        return f"{kana}さい"

    def people_text(people: int) -> str:
        if people == 1:
            return "ひとり"
        if people == 2:
            return "ふたり"
        if people == 4:
            return "よにん"
        return f"{hiragana_number(people)}にん"

    for age in range(20, 50):
        texts.update((f"{age_text(age)}です。", f"{age_text(age)}です。あなたは。"))
    for people in range(1, 9):
        texts.update((f"{people_text(people)}です。", f"{people_text(people)}です。あなたのかぞくは。"))
    for number in range(1, 101):
        kana = hiragana_number(number)
        texts.update((
            f"{kana}ばんです。", f"{kana}ばんですね。", f"{kana}ページです。",
            f"{kana}てんでした。",
        ))

    # Literal lines are collected directly. Dynamic variants above are generated
    # explicitly so every speaker button and sequential full-dialogue playback is offline.
    dialogue_lines = re.findall(r'e\("([^"$]+[。！？])",\s*"', source)
    texts.update(dialogue_lines)
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
