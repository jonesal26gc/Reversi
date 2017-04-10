
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {

    public String getUserInputString(String prompt) {
        /**************************************************************
         Retrieve some input from the user after displaying prompt.
         **************************************************************/

        String inputLine = "";
        System.out.print(prompt + " > ");

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0 ) {
                return "";
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }

        return inputLine;
    }
}