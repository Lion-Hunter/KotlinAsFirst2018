@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import lesson5.task1.findCheapestStuff
import kotlin.math.max

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */

val datesList = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря")

fun dateStrToDigit(str: String): String {
    val date = str.split(" ")
    if ((date.size != 3) || (date[1] !in datesList)
            || date[0].contains(Regex("""[^\d]""")) || date[2].contains(Regex("""[^\d]""")))
        return ""

    val number = date[0].toInt()

    if (daysInMonth(datesList.indexOf(date[1]) + 1, date[2].toInt()) < number)
        return ""
    val numOfMonth = datesList.indexOf(date[1]) + 1

    return String.format("%02d.%02d.%s", number, numOfMonth, date[2])
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */

fun dateDigitToStr(digital: String): String = try {
    val date = digital.split(".")
    val first = date[0].toInt()
    val second = date[1].toInt()
    val third = date[2].toInt()

    if ((second !in 1..12) || digital.contains(Regex("""[^\d.]"""))
            || date.size != 3 || daysInMonth(second, third) < first) throw NumberFormatException()
    else
        String.format("%d %s %s", first, datesList[second - 1], date[2])
} catch (e: NumberFormatException) {
    ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String =
        if (Regex("""^(\+\d+)?(\(\d+\))\d+|\d+""")
                        .matches(phone.replace(Regex("""[\s-]"""), "")))
            phone.replace(Regex("""[()\s-]"""), "")
        else ""

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val str = jumps.replace(Regex("""%\s?|-\s?"""), "").trim()
    val str1 = str.replace((Regex("""\s\s+""")), " ")
    val results = str1.split(" ")

    for (result in results)
        if (!result.contains(Regex("""[\d]"""))) return -1

    return results.map { it.toInt() }.max()!!.toInt()
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val jump = jumps.replace(Regex("""\d+\s%+-|\d\s-|\d+\s%+\s+|\d+\s%+-?$"""), "")
    var results = jump.replace(Regex("""\s%?\+|\s%+\+"""), "")
    results = results.replace(Regex("""\s+"""), " ").trim()
    if (!results.contains(Regex("""[\d\s-%]"""))) return -1
    val parse = results.split(" ")

    return parse.map { it.toInt() }.max()!!
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if ((expression.contains(Regex("""[^\s\d-+]"""))) || (expression.isEmpty() || !expression.contains(Regex("""^\d""")))
            || (expression.contains(Regex("""\+\s\+|-\s-|\+\s-|-\s\+|\d+\s\s\d+|^\+\s?\d|^-\s?\d|^\s$"""))))
        throw IllegalArgumentException()

    var sum = 0
    var exp = expression.replace(Regex("""\+\s"""), "+")
    exp = exp.replace(Regex("""-\s"""), "-")
    val results = exp.split(" ")

    for (result in results) {
        when {
            result[0] in '0'..'9' -> sum += result.toInt()
            result[0] == '+' -> sum += result.substring(1, result.length).toInt()
            else -> sum -= result.substring(1, result.length).toInt()
        }
    }

    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val list = str.toLowerCase().split(" ")
    var result = 0

    for (i in 0 until list.size - 1) {
        if (list[i] == list[i + 1]) return result
        result += list[i].length + 1
    }

    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (description.isEmpty() || description.contains(Regex("""^(\w+\s\d+\.?(\d+)*\s)*$"""))) return ""

    val map = mutableMapOf<String, Double>()
    val products = description.replace(Regex(""";"""), "")
    val list = products.split(" ")
    var max = 0.0
    var result = ""

    for (i in 0 until list.size) {
        if (list[i].contains(Regex("""\d+.?\d?""")) && (i % 2 != 0))
            map[list[i - 1]] = list[i].toDouble()
    }

    map.forEach {
        if (it.value >= max) {
            result = it.key
            max = it.value
        }
    }

    return result
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (roman.contains(Regex("""[^IVXLCDM]"""))) return -1
    val symbol = mapOf("M" to 1000, "D" to 500, "C" to 100, "L" to 50, "X" to 10, "V" to 5, "I" to 1)
    val twoSymbols = mapOf("CM" to 200, "CD" to 200, "XC" to 20, "XL" to 20, "IX" to 2, "IV" to 2)
    var result = 0
    roman.forEach { result += symbol[it.toString()]!! }

    twoSymbols.keys.forEach { if (it in roman) result -= twoSymbols[it]!! }

    return if (result != 0) result
    else -1
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var count1 = 0
    var count2 = 0
    for (i in commands)
        when (i) {
            '[' -> count1 += 1
            ']' -> count2 += 1
        }
    if (commands.contains(Regex("""[^\d><\[\]+\s-]""")) || count1 != count2) throw IllegalArgumentException()


    val open = emptyList<Int>().toMutableList()
    val pairs = mutableListOf<Pair<Int, Int>>()
    for (bracket in 0 until commands.length) {
        when (commands[bracket]) {
            '[' -> open += bracket
            ']' -> {
                if (open.isEmpty()) throw IllegalArgumentException()
                else {
                    pairs += open.last() to bracket
                    open -= open.last()
                }
            }
        }
    }

    val conveyor = mutableListOf<Int>()
    for (cell in 0 until cells)
        conveyor += 0

    var pos = cells / 2
    var operations = 0
    var num = 0
    while (num in 0 until commands.length && operations < limit) {
        operations += 1
        when (commands[num]) {
            '+' -> conveyor[pos]++
            '-' -> conveyor[pos]--
            '<' -> {
                pos -= 1
                if (pos !in 0..(cells - 1))
                    throw IllegalStateException()
            }
            '>' -> {
                pos += 1
                if (pos !in 0..(cells - 1))
                    throw java.lang.IllegalStateException()
            }
            '[' -> if (conveyor[pos] == 0)
                num = pairs.find { it.first == num }!!.second
            ']' -> if (conveyor[pos] != 0)
                num = pairs.find { it.second == num }!!.first
        }

        num++
    }
    return conveyor
}
