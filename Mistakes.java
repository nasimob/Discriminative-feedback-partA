import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

public class Mistakes {
  private List<Double> graph;

  public Mistakes() {
    graph = new ArrayList<>();
  }

  public int[] getValues(int precision) {
    precision = (int) Math.pow(10, precision);

    int[] values = new int[graph.size()];
    for (int i = 0; i < graph.size(); i++)
      values[i] = (int) (graph.get(i) * precision);

    return values;
  }

  public void add(double mistake) {
    graph.add(mistake);
  }

  public void printGraph() {
    for (double data : graph)
      System.out.println(data);
  }
}