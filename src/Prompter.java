import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Prompter {
    private BufferedReader mReader;
    private String mOperator;
    private List<Float> mNumbers;

    public Prompter() {
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mOperator = null;
        mNumbers = new ArrayList<>();
    }

    public float number() {
        System.out.println("Please enter a number");
        String number = "";
        try {
            number = mReader.readLine().trim().toLowerCase();
            while (!number.matches("(^c$)||(^-?+\\d*\\.?\\d+$)")) {
                System.out.println("Error entering number or C. Use . for decimals. Please try again. ");
                number = mReader.readLine().trim().toLowerCase();
            }
            while (number.length() == 0) {
                System.out.println("Did not enter anything. Please try again. ");
                number = mReader.readLine().trim().toLowerCase();
            }
        } catch (IOException e) {
            System.out.println("Did not enter a number");
            e.printStackTrace();
        }
        if (number.contains("c")) {
            c();
        }
        return Float.parseFloat(number);
    }

    public void operand() {
        System.out.println("Please enter an operator");
        String operand = "";
        try {
            operand = mReader.readLine().trim().toLowerCase();
            while (!operand.matches("(^(\\*||\\+||\\-||/||=||c)$)")) {
                System.out.println("Error entering operator (+,-,* or /) or C. Please try again. ");
                operand = mReader.readLine().trim().toLowerCase();
            }
            while (operand.length() == 0) {
                System.out.println("Did not enter anything. Please try again. ");
                operand = mReader.readLine().trim().toLowerCase();
            }
        } catch (IOException e) {
            System.out.println("Did not enter an operator");
            e.printStackTrace();
        }
        if (operand.contains("c")) {
            c();
        }
        mOperator = operand;
    }

    public void c() {
        System.out.println("0");
        mNumbers.clear();
        run();
    }

    public float calculations() {
        float result = 0;
        if (mOperator.contains("+")) {
            result = mNumbers.get(mNumbers.size() - 2) + mNumbers.get(mNumbers.size() - 1);
        }
        if (mOperator.equals("-")) {
            result = mNumbers.get(mNumbers.size() - 2) - mNumbers.get(mNumbers.size() - 1);
        }
        if (mOperator.contains("*")) {
            result = mNumbers.get(mNumbers.size() - 2) * mNumbers.get(mNumbers.size() - 1);
        }
        if (mOperator.contains("/")) {
            while (mNumbers.get(mNumbers.size() - 1) == 0) {
                System.out.println("Dividing by 0 is not the best idea, please enter a number again");
                mNumbers.remove(mNumbers.size() - 1);
                mNumbers.add(number());
            }
            result = mNumbers.get(mNumbers.size() - 2) / mNumbers.get(mNumbers.size() - 1);
        }
        return result;
    }

    public void run() {
        mNumbers.add(number());
        operand();
        while (!mOperator.contains("=")) {
            mNumbers.add(number());
            System.out.println("= " + calculations());
            mNumbers.add(calculations());
            operand();
        }
        System.out.println("= " + calculations());
        System.exit(0);
    }
}