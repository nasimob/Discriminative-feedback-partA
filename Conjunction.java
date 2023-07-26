import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

class Conjunction {
  private List<Integer> conjunction;

  public Conjunction() {
    conjunction = new LinkedList();
  }

  List<Integer> getList() {
    return conjunction;
  }

  public void add(int c) throws RuntimeException {
    if (conjunction.contains(c) || conjunction.contains(-c))
      throw new RuntimeException(
          "the conjunction already contains attribute #" + c + " or #" + (-c) + ": " + conjunction.toString());

    conjunction.add(c);
  }

  public boolean isTrue(boolean[] attributes) {
    for (int i = 0; i < attributes.length; i++)
      if ((attributes[i] && conjunction.contains(-(i+1))) || (!attributes[i] && conjunction.contains(i+1)))
        return false;

    return true;
  }

  public String toString() {
    return conjunction.toString();
  }
}