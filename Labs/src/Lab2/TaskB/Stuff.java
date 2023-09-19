package Lab2.TaskB;

public class Stuff {
  private int price;

  Stuff() {
    price = (int)(Math.random() * 200 + 100);
  }

  public int getPrice() {
    return price;
  }
}
