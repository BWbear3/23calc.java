import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        //while (true) {
            //String exit = "выход"; /////////////////////////////////////
            String B1;
            String oper;
            String B2;
            System.out.println("Введите уравнение:");
            Scanner scanner = new Scanner(System.in);
            String[] massiv = scanner.nextLine().split(" ");
            B1 = massiv[0];
            //if (exit.equals(B1)) break;//////////////////////////
            Calculation d3 = new Calculation();
            RomanConvertArabic d1 = new RomanConvertArabic();
            ArabicConvertRoman d2 = new ArabicConvertRoman();
            if (massiv.length > 1) {
                oper = massiv[1];
                //if (exit.equals(oper)) break;//////////////////////////
            } else {
                throw new RuntimeException("т.к. введенная строка не является математической операцией");
            }
            if (massiv.length > 2) {
                B2 = massiv[2];
                //if (exit.equals(B2)) break;//////////////////////////
            } else {
                throw new RuntimeException("т.к. введенная строка не является математической операцией");
            }
            RomanNumberTest testRom = new RomanNumberTest();
            ArabicNumberTest testInt = new ArabicNumberTest();
            boolean tr1 = testRom.rom_arab2(B1);
            boolean tr2 = testRom.rom_arab2(B2);
            boolean ti1 = testInt.AraTest(B1);
            boolean ti2 = testInt.AraTest(B2);
            if ((tr1 && !ti1 && !tr2 && ti2) || (!tr1 && ti1 && tr2 && !ti2)) // проверка инты+римляне
            {
                throw new ArithmeticException("т.к используются одновременно разные системы счисления");
            }
            if ((!tr1 && !ti1 && !tr2 && !ti2) || (!tr1 && ti1 && !tr2 && !ti2) || (!tr1 && !ti1 && !tr2 && ti2) || (tr1 && !ti1 && !tr2 && !ti2) || (!tr1 && !ti1 && tr2 && !ti2)) // проверка на крокозябру
            {
                throw new ArithmeticException("т.к введены некорректные данные, допустимый диапазон значений [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10][I, II, III, IV, V, VI, VII, VIII, IX, X]");
            }
            if (ti1 && ti2) {
                int N1 = Integer.parseInt(B1);
                int N2 = Integer.parseInt(B2);
                int itog = d3.calculation(oper, N1, N2);
                System.out.println("Результат:" + itog);
            } else {
                int N1 = d1.Rom_arab(B1);
                int N2 = d1.Rom_arab(B2);
                int itog = d3.calculation(oper, N1, N2);
                if (itog <= 0) {
                    throw new ArithmeticException("т.к. в римской системе счисления отсутствуют числа <=0");
                }
                String result = d2.convert(itog);

                System.out.println("Результат:" + result);
            }
        //}
    }
}

class Calculation {
    int calculation(String oper, int N1, int N2) //считалочка
    {
        int ITOG;
        switch (oper) {
            case "+":
                ITOG = N1 + N2;
                break;
            case "-":
                ITOG = N1 - N2;
                break;
            case "*":
                ITOG = N1 * N2;
                break;
            case "/":
                if (N2 > 0) {
                    ITOG = N1 / N2;
                } else {
                    throw new ArithmeticException("т.к Бесконечность так-то, но тз душное");
                }
                break;
            default:
                throw new IllegalArgumentException("Т.к не коректно введен оператор в уравнении");
        }
        return ITOG;
    }
}

class ArabicNumberTest {
    boolean AraTest(String B1) {
        int N1;
        try {
            N1 = Integer.parseInt(B1);
            String[] massiv = B1.split(""); // проверка на нолик в начале
            if (massiv[0].equals("0") || N1 > 10) {
                throw new ArithmeticException("т.к допустимый диапазон значений [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}// проверка на интовость

class RomanNumberTest {
    boolean rom_arab2(String B2) {  // для проверки
        int N2 = 0;
        int[] decimal = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < decimal.length; i++) {
            while (B2.indexOf(roman[i]) == 0) {
                N2 += decimal[i];
                B2 = B2.substring(roman[i].length());
            }
        }

        if (N2 > 0) {
            return true;
        } else {
            return false;
        }
    }
} // проверка на то что число римское

class RomanConvertArabic {
    int Rom_arab(String B1) { // перевод римлян в арабов
        int N1 = 0;
        int[] decimal = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int B3 = B1.length();
        if (B3 > 3 || B1.equals("0") || B1.equals("VV")) {
            throw new RuntimeException("т.к допустимый диапазон значений [I, II, III, IV, V, VI, VII, VIII, IX, X]");
        }
        for (int i = 0; i < decimal.length; i++) {
            while (B1.indexOf(roman[i]) == 0) {
                N1 += decimal[i];
                B1 = B1.substring(roman[i].length());
            }
        }
        if (B1 == null || B1.isEmpty() || B1.trim().isEmpty()) {
            return N1;
        } else {
            throw new RuntimeException("т.к допустимый диапазон значений [I, II, III, IV, V, VI, VII, VIII, IX, X]");
        }
    }
}//перевод римлян в арабов для вычислений

class ArabicConvertRoman {
    String romanDigit(int n, String one, String five, String ten) {

        if (n >= 1) {
            if (n == 1) {
                return one;
            } else if (n == 2) {
                return one + one;
            } else if (n == 3) {
                return one + one + one;
            } else if (n == 4) {
                return one + five;
            } else if (n == 5) {
                return five;
            } else if (n == 6) {
                return five + one;
            } else if (n == 7) {
                return five + one + one;
            } else if (n == 8) {
                return five + one + one + one;
            } else if (n == 9) {
                return one + ten;
            }

        }
        return "";
    } // часть 1 перевода арабов в римлян

    String convert(int ITOG) {

        String romanOnes = romanDigit(ITOG % 10, "I", "V", "X");
        ITOG /= 10;
        String romanTens = romanDigit(ITOG % 10, "X", "L", "C");
        ITOG /= 10;
        String romanHundreds = romanDigit(ITOG % 10, "C", "D", "M");


        String result = romanHundreds + romanTens + romanOnes;
        return result;

    }
}
