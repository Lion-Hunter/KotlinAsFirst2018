@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val reader = File(inputName).bufferedReader().readLines().joinToString().toLowerCase()

    for (string in substrings) {
        val i = 0
        var count = 0
        var entering = Regex(string.toLowerCase()).find(reader, 0)
        while (entering != null && i <= reader.length) {
            count++
            entering = Regex(string.toLowerCase()).find(reader, entering.range.first + 1)
        }
        result[string] = count
    }

    return result
}

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val reader = File(inputName).bufferedReader()
    val writer = File(outputName).bufferedWriter()
    var symbol = reader.read()
    var lastSymbol = ' '

    while (symbol != -1) {
        var sym = symbol.toChar()
        if (lastSymbol == 'Ж' || lastSymbol == 'ж'
                || lastSymbol == 'Ш' || lastSymbol == 'ш') {
            when (sym) {
                'Я' -> sym = 'А'
                'я' -> sym = 'а'
                'Ю' -> sym = 'У'
                'ю' -> sym = 'у'
                'Ы' -> sym = 'И'
                'ы' -> sym = 'и'
            }
        } else if (lastSymbol == 'Ч' || lastSymbol == 'ч'
                || lastSymbol == 'Щ' || lastSymbol == 'щ') {
            when (sym) {
                'Я' -> sym = 'А'
                'я' -> sym = 'а'
                'Ю' -> sym = 'У'
                'ю' -> sym = 'у'
                'Ы' -> sym = 'И'
                'ы' -> sym = 'и'
            }
        }

        writer.write(sym.toInt())
        lastSymbol = sym
        symbol = reader.read()
    }

    reader.close()
    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var maxLength = 0
    val out = File(outputName).bufferedWriter()

    for (line1 in File(inputName).readLines())
        if (line1.trim().length > maxLength) maxLength = line1.trim().length

    for (line2 in File(inputName).readLines()) {
        if (maxLength > line2.trim().length)
            out.write(" ".repeat((maxLength + line2.trim().length) / 2 - line2.trim().length))

        out.write(line2.trim())
        out.newLine()
    }

    out.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val reader = File(inputName).bufferedReader().readLines()
    val writer = File(outputName).bufferedWriter()
    var maxString = 0
    for (line in reader) {
        val string = line.replace(Regex(("""\s+""")), " ").trim()
        if (string.length > maxString)
            maxString = string.length
    }

    for (line in reader) {
        val string = line.replace(Regex("""\s+"""), "").trim()
        val list = line.trim().split(Regex("""\s+"""))
        val number = list.size - 1
        if (number <= 0)
            writer.write(string)
        else {
            val spaces = (maxString - string.length) / (number)
            val remainder = (maxString - string.length) % (number)

            for (word in 0 until list.size) {
                writer.write(list[word])

                if (word == number)
                    continue
                else {
                    writer.write(" ".repeat(spaces))

                    if (word < remainder)
                        writer.write(" ")
                }
            }
        }

        writer.newLine()
    }

    writer.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val reader = File(inputName).bufferedReader().readLines().joinToString().toLowerCase()
    if (reader.isEmpty()) return emptyMap()
    val wordsMap = mutableMapOf<String, Int>()
    val string = reader.replace(Regex("""[^a-zа-яё]+"""), " ").trim()
    val words = string.split(Regex("""\s+"""))
    val result = mutableMapOf<String, Int>()

    if (words.size <= 20) {
        for (word in words) {
            result.getOrPut(word) { 0 }
            result[word] = result[word]!! + 1
        }
    }

    for (word in words) {
        wordsMap.getOrPut(word) { 0 }
        wordsMap[word] = wordsMap[word]!! + 1
    }

    for (i in 0..19) {
        var max = 0
        var w = ""

        wordsMap.forEach {
            if (it.value >= max) {
                max = it.value
                w = it.key
            }
        }

        result[w] = max
        wordsMap.remove(w)
    }

    if ("" in result) result.remove("")
    return result
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val reader = File(inputName).bufferedReader().readLines()
    val writer = File(outputName).bufferedWriter()
    val wordsMap = mutableMapOf<String, Int>()
    var maxLength = 0
    val result = mutableListOf<String>()

    for (read in reader) {
        val line = read.toLowerCase()
        val symbols = emptyList<Char>().toMutableList()
        var length = 0

        for (i in line) {
            if (i in symbols) {
                length = 0
                break
            } else {
                length += 1
                symbols += i
            }
        }

        if (length == read.length) {
            wordsMap[read] = length
            result += read
            if (length > maxLength)
                maxLength = length
        }

    }

    wordsMap.forEach {
        if (it.value != maxLength)
            result.remove(it.key)
    }

    writer.write(result.joinToString(separator = ", "))
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val reader = File(inputName).bufferedReader().readLines()
    val writer = File(outputName).bufferedWriter()
    var i = 0
    var b = 0
    var s = 0
    var p = 0
    var count = 0

    writer.write("<html><body>")
    for (line in reader) {

        if (line.isEmpty()) {
            if (p != 0) writer.write("</p><p>")
            else writer.write("<p>")
            p = 1
            continue
        }

        if (p == 0) {
            writer.write("<p>")
            p = 1
        }

        loop@ for (sym in 0 until line.length) {
            var check = 0
            when (line[sym]) {
                '*' -> {
                    count += 1
                    if (line[sym + 1] == '*') {
                        check += 1
                        continue@loop
                    } else if (count == 3) {
                        if (i == 0 && b == 0) {
                            writer.write("<b><i>")
                            b = 1
                            i = 2
                        } else {
                            if (b > i)
                                writer.write("</b></i>")
                            else
                                writer.write("</i></b>")
                            b = 0
                            i = 0
                        }
                    } else if (line[sym + 1] != '*' && count == 2) {
                        b = if (b == 0) {
                            writer.write("<b>")
                            if (i == 1) 2
                            else {
                                1
                            }

                        } else {
                            writer.write("</b>")
                            0
                        }
                        check = 0
                    } else if (line[sym + 1] != '*' && count == 1) {
                        i = if (i == 0) {
                            writer.write("<i>")

                            if (b == 1) 2
                            else 1
                        } else {
                            writer.write("</i>")
                            0
                        }
                        check = 0
                    }
                }
                '~' -> {
                    when (s) {
                        0, 2 -> {
                            s += 1
                            continue@loop
                        }
                        1 -> {
                            s += 1
                            writer.write("<s>")
                        }
                        3 -> {
                            writer.write("</s>")
                            s = 0
                        }
                    }
                }
                else -> writer.write("${line[sym]}")
            }

            if (check == 0)
                count = 0
        }

        writer.newLine()
    }

    if (p == 1)
        writer.write("</p>")

    writer.write("</body></html>")
    writer.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/*val reader = File(inputName).bufferedReader().readLines()
    val writer = File(outputName).bufferedWriter()
    var x = 0
    val ul = listOf(0, 0, 0, 0, 0, 0).toMutableList()
    val ol = listOf(0, 0, 0, 0, 0, 0).toMutableList()

    writer.write("<html><body>")
    for (line in reader) {

        val value = x * 4
        if (line.substring(value, (value + 3)) == "    ") {
            x += 1
            if (line[value + 3] == '*') {
                writer.write("<ul>${line.substring(value + 4, line.length - 1)}")
                ul[x] = 1
            } else {
                writer.write("<ol><li>${line.substring(value + 5, line.length - 1)}")
            }
        } else if (line.substring(value - 4, (value - 1)) == "    ") {
            x -= 1
            if (ul[x + 1] == 1) {
                writer.write("</ul>${line.substring(value + 4, line.length - 1)}")
                ul[x + 1] = 0
            } else {
                writer.write("<ol>${line.substring(value + 5, line.length - 1)}")
            }
        }
    }*/

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

