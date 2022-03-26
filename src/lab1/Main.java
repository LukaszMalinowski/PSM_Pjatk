package lab1;

public class Main {

    public static void main(String[] args) {
        validateInput(args);

        String unit = args[0];
        if (!unit.equals("rad") && !unit.equals("deg")) {
            throw new IllegalArgumentException("Accepted units are 'rad' for radians and 'deg' for degrees");
        }

        double parsedValue = Double.parseDouble(args[1]);
        double radValue = parsedValue;
        if (unit.equals("deg")) {
            radValue = calculateValueInRadians(radValue);
        }

        double result = calculateSinValue(radValue);

        double mathSinValue = Math.sin(radValue);
        System.out.printf("Final result of sin(x) when x is %s %s = %s %s", parsedValue, unit, result, System.lineSeparator());
        System.out.printf("Final java Math.sin() value is %s", mathSinValue);
    }

    private static void validateInput(String[] args) throws IllegalArgumentException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Program needs two arguments. First - unit ('rad' or 'deg') and second - value");
        }
    }

    private static double calculateValueInRadians(double value) {
        return Math.toRadians(value);
    }

    private static double calculateSinValue(double value) {
        value = getTransformedValue(value);

        double sum = value;
        double sign = -1d;
        int power = 3;
        for (int i = 1; i <= 10; i++) {
            sum += getTaylorValue(value, sign, power);

            printResults(value, sum);
            sign = sign * -1;
            power += 2;
        }

        return sum;
    }

    private static double getTransformedValue(double value) {
        double normalizedValue = value % (2d * Math.PI);

        if (0 < normalizedValue && normalizedValue <= 0.5 * Math.PI) {
            return normalizedValue;
        } else if (0.5 * Math.PI < normalizedValue && normalizedValue <= Math.PI) {
            return Math.PI - normalizedValue;
        } else if (Math.PI < normalizedValue && normalizedValue <= 1.5 * Math.PI) {
            return -1. * (normalizedValue - Math.PI);
        } else {
            return -1. * (2 * Math.PI - normalizedValue);
        }
    }

    private static double getTaylorValue(double value, double sign, int power) {
        return sign * (Math.pow(value, power) / factorial(power));
    }

    private static long factorial(int number) {
        long result = 1;
        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }
        return result;
    }

    private static void printResults(double value, double sum) {
        double realResult = Math.sin(value);
        System.out.printf("Current result of sin(x) when x is %s = %s %s", value, sum, System.lineSeparator());
        System.out.printf("Current java Math.sin() value is %s %s", realResult, System.lineSeparator());
        System.out.printf("Difference is %s %s", Math.abs(realResult - sum), System.lineSeparator());
        System.out.println("========================");
    }

}
