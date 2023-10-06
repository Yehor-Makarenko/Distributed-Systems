// package Lab4.TaskA.Java.Phonebook;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.InputStream;
// import java.net.URISyntaxException;
// import java.util.Properties;

// public class Phonebook {
//   private static Properties props = new Properties();
//   private static File db = new File("Labs/src/Lab4/TaskA/resources/database.properties");    
//   private static InputStream is;
//   private static FileWriter fos;

//   static public String getPhoneByName(String name) {
//     String nameWithoutSpaces = name.replaceAll(" ", "_");
//     String phone;

//     try {
//       is = Thread.currentThread().getClass().getResourceAsStream("/Lab4/TaskA/resources/database.properties");
//       props.load(is);
//       is.close();
//     } catch (IOException e) {      
//       e.printStackTrace();
//     }

//     phone = props.getProperty(nameWithoutSpaces);
//     return phone;        
//   }

//   static public String getNameByPhone(String phone) {    
//     String name;

//     try {
//       is = Thread.currentThread().getClass().getResourceAsStream("/Lab4/TaskA/resources/database.properties");
//       props.load(is);
//       is.close();
//     } catch (IOException e) {      
//       e.printStackTrace();
//     }

//     name = props.getProperty(phone);
//     return name;        
//   }

//   static public void addPerson(String name, String phone) {
//     String nameWithoutSpaces = name.replaceAll(" ", "_");    

//     try {
//       is = Thread.currentThread().getClass().getResourceAsStream("/Lab4/TaskA/resources/database.properties");      
//       fos = new FileWriter(db);      
//       props.load(is);
//       props.setProperty(nameWithoutSpaces, phone);
//       props.setProperty(phone, nameWithoutSpaces);
//       props.store(fos, "");
//       is.close();
//       fos.close();
//     } catch (IOException e) {
//       e.printStackTrace();
//     }
//   }

//   static public void removePersonByName(String name) {
//     String nameWithoutSpaces = name.replaceAll(" ", "_");
//     try {
//       is = Thread.currentThread().getClass().getResourceAsStream("/Lab4/TaskA/resources/database.properties");
//       fos = new FileWriter(db);
//       props.load(is);
//       props.remove(nameWithoutSpaces);      
//       props.store(fos, null);
//       is.close();
//       fos.close();
//     } catch (IOException e) {
//       e.printStackTrace();
//     }
//   }
// }

package Lab4.TaskA.Java.Phonebook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Phonebook {
  private static Properties props = new Properties();
  private static File db = new File("Labs/src/Lab4/TaskA/resources/database.properties");    
  private static InputStream is;
  private static FileOutputStream fos;

  static public String getPhoneByName(String name) {
    String nameWithoutSpaces = name.replaceAll(" ", "_");
    String phone;

    try {
      is = Thread.currentThread().getClass().getResourceAsStream("/Lab4/TaskA/resources/database.properties");
      props.load(is);
      is.close();
    } catch (IOException e) {      
      e.printStackTrace();
    }

    phone = props.getProperty(nameWithoutSpaces);
    return phone;        
  }

  static public String getNameByPhone(String phone) {    
    String name;

    try {
      is = Thread.currentThread().getClass().getResourceAsStream("/Lab4/TaskA/resources/database.properties");
      props.load(is);
      is.close();
    } catch (IOException e) {      
      e.printStackTrace();
    }

    name = props.getProperty(phone);
    return name;        
  }

  static public void addPerson(String name, String phone) {
    String nameWithoutSpaces = name.replaceAll(" ", "_");    

    try {
      is = new FileInputStream(db);
      fos = new FileOutputStream(db);      
      props.load(is);
      // props.clear();
      props.setProperty(nameWithoutSpaces, phone);
      props.setProperty(phone, nameWithoutSpaces);
      props.store(fos, "");
      is.close();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static public void removePersonByName(String name) {
    String nameWithoutSpaces = name.replaceAll(" ", "_");
    try {
      is = Thread.currentThread().getClass().getResourceAsStream("/Lab4/TaskA/resources/database.properties");
      fos = new FileOutputStream(db);
      props.load(is);
      props.remove(nameWithoutSpaces);      
      props.store(fos, null);
      is.close();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}