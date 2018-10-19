@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.accountInThreeYears
import lesson1.task1.discriminant
import lesson3.task1.isPrime
import kotlin.math.pow
import kotlin.math.*
import kotlin.system.measureTimeMillis

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var result = 0.0
    for (i in v) {
        result += i * i
    }
    return sqrt(result)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    if (list.isEmpty()) return 0.0
    return list.sum() / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isEmpty()) return list
    val average = mean(list)
    for (i in 0 until list.size) list[i] -= average
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    if (a.isEmpty() || b.isEmpty()) return 0.0
    var result = 0.0
    for (i in 0 until a.size) result += a[i] * b[i]
    return result
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double = p.map { it * x.pow(p.indexOf(it)) }.sum()

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    var x = 0.0
    var count = 0.0
    for (i in 0 until list.size) {
        x = list[i]
        list[i] += count
        count += x
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    if (isPrime(n)) return listOf(n)
    val result = mutableListOf<Int>()
    var x = n
    var i = 2
    while (n >= i) {
        while (x % i == 0) {
            x /= i
            result.add(i)
        }
        i++
    }
    return result
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")
/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */

fun toCh(n: Int): String {
    val x = n - 10
    val list = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
    return list[x]
}

fun reversToCh(x: String): Int {
    val list = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
    return list.indexOf(x)
}

fun con(n: Int, base: Int, q: Int): List<String> {
    val result = mutableListOf<String>()
    var x = n
    var count = String()
    if (x < base) {
        return if (x > 9) listOf(toCh(x)) else listOf((x % base).toString())
    }
    while (x > 0) {
        if (q == 1) {
            count = (x % base).toString()
            result.add(count)
            x /= base
        } else {
            count = if (x % base > 9) toCh(x % base) else (x % base).toString()
            result.add(count)
            x /= base
        }
    }
    return result.reversed()
}

fun convert(n: Int, base: Int): List<Int> {
    if (n == 1) return listOf(n)
    val q = 1
    val result = mutableListOf<Int>()

    val list = con(n, base, q)
    for (i in 0 until list.size) {
        result.add(list[i].toInt())
    }
    return result
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String = (con(n, base, 0)).joinToString(separator = "")

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var result = 0.0
    var count = 0
    for (i in 0..digits.size - 2) {
        count = digits[i]
        result += count.toDouble() * base.toDouble().pow(digits.size - 1 - i)
    }

    result += if (digits.last() == 0) 0 else digits.last()
    return result.toInt()
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val list = mutableListOf<Int>()
    for (i in str) {
        if (i.toInt() <= '9'.toInt()) list.add(i.toInt() - 48) else list.add((reversToCh(i.toString()) + 10))
    }
    return decimal(list, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    val digits = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val rom = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    var x = n
    var result = ""
    var count = 0
    while (x > 0) {
        while (x >= digits[count]) {
            result += rom[count]
            x -= digits[count]
        }
        count++
    }
    return result
}
/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun russian(n: Int): String {
    val units = listOf("один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val tens = listOf("двадцать", "тридцать", "сорок", "пятьдесят",
            "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
    val tenToTwenty = listOf("десять", "одиннадцать", "двенадцать", "тринадцать",
            "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
    val hundreds = listOf("сто", "двести", "триста", "четыреста",
            "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот")
    val thNumber = listOf("одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val thForm = listOf("тысяча", "тысячи", "тысяч")

    var x = n
    var result = ""

    if (x > 99999) {
        result += """${hundreds[x / 100000 - 1]} """
        if (x % 100000 / 1000 == 0) {
            result += when (x % 10000 / 1000) {
                1 -> """${thForm[0]} """
                2, 3, 4 -> """${thForm[1]} """
                else -> """${thForm[2]} """
            }
        }
        x %= 100000
    }

    if (x > 9999) {
        if (x / 10000 > 1) {
            result += """${tens[x / 10000 - 2]} """
            x %= 10000
        } else {
            val q = x % 10000 / 1000
            result += """${tenToTwenty[q]} """
            result += when (x % 10000 / 1000) {
                1 -> """${thForm[0]} """
                2, 3, 4 -> """${thForm[1]} """
                else -> """${thForm[2]} """
            }
            x %= 1000
        }

    }

    if (x > 999) {
        result += """${thNumber[x / 1000 - 1]} """
        result += when (x / 1000) {
            1 -> """${thForm[0]} """
            2, 3, 4 -> """${thForm[1]} """
            else -> """${thForm[2]} """
        }
        x %= 1000
    }

    if (x > 99) {
        result += """${hundreds[x / 100 - 1]} """
        x %= 100
    }

    if (x > 9) {
        if (x / 10 > 1) {
            result += """${tens[x / 10 - 2]} """
            x %= 10
        } else {
            result += """${tenToTwenty[x % 10]} """
            x = 0
        }
    }

    if (x > 0) {
        result += """${units[x - 1]} """
    }

    return result.substring(0, result.length - 1)
}