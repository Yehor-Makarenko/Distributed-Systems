package Lab4.TaskA.Java;

import java.io.IOException;

import Lab4.TaskA.Java.Phonebook.Phonebook;

public class Main {
  public static void main(String[] args) throws IOException {
    Phonebook.addPerson("IIIVVV", "45");
    // Phonebook.addPerson("III", "43");
    // Phonebook.addPerson("IIIV", "4556346");
    System.out.println(Phonebook.getPhoneByName("III"));
    System.out.println(Phonebook.getPhoneByName("IIIV"));
  }
}
