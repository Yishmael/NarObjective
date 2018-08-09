package unused;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import types.EventType;

public class Loader {

    public static void loadQuests() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("res/other/quests.txt"));

        while (br.readLine() != null) {
            System.out.println("Name: " + br.readLine());
            System.out.println("Dialogue0: " + br.readLine());
            System.out.println("Dialogue1: " + br.readLine());
            System.out.println("Dialogue2: " + br.readLine());
            System.out.println("Description: " + br.readLine());
            System.out.println();

            int numObjectives = Integer.parseInt(br.readLine());
            for (int i = 0; i < numObjectives; i++) {
                String type = br.readLine();
                System.out.println("Type: " + type);
                if (type.equals(EventType.obtaining.toString()) || type.equals(EventType.using.toString())) {
                    System.out.println("Obj: " + br.readLine());
                    System.out.println("Item: " + br.readLine());
                    System.out.println("Qnt: " + br.readLine());
                } else if (type.equals(EventType.exploring.toString())) {
                    System.out.println("Obj: " + br.readLine());
                    System.out.println("Zone: " + br.readLine());
                } else if (type.equals(EventType.talking.toString())) {
                    System.out.println("Obj: " + br.readLine());
                    System.out.println("NPC: " + br.readLine());
                } else {
                    System.out.println("Unknown quest type: " + type);
                    break;
                }
                System.out.println();
            }

        }
        br.close();
    }

}
