import java.util.LinkedList;
import java.lang.Math;
import java.awt.*;
import java.io.IOException;//same as above
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Main {
  public static void main(String[] args) {
    String csvFilePath = "./zoo.csv";
    int GRAPH_PRECISION = 3;

    Teacher mostDiscriminativeTeacher = new Teacher() {
      protected int getIndex(Data dataToClassify, Rule rule, List<Integer> diff) {
        Data exampleData = rule.getExplanation();
        
        int maxDiff = -1;
        int maxDiffIndex = -1;

        for (int diffFeature : diff) {
          int countToClassify = 0;
          int countExample = 0;

          for (Data data : dataList)
            if (data.getAttributes()[(int)Math.abs(diffFeature) - 1]) {
              if (data.getClassification().equals(dataToClassify.getClassification()))
                countToClassify++;
              else if (data.getClassification().equals(exampleData.getClassification()))
                countExample++;
            }

          int difference = (int) Math.abs(countToClassify - countExample);
          if (maxDiff < difference) {
            maxDiff = difference;
            maxDiffIndex = diffFeature;
          }
        }

        return diff.indexOf(maxDiffIndex);
      }
    };

    Teacher randomTeacher = new Teacher() {
      protected int getIndex(Data dataToClassify, Rule rule, List<Integer> diff) {
        return (int) (Math.random() * diff.size());
      }
    };

    Mistakes graph1 = learn(csvFilePath, randomTeacher);
    Mistakes graph2 = learn(csvFilePath, mostDiscriminativeTeacher);
    GraphGenerator.createGraph(graph1.getValues(GRAPH_PRECISION), graph2.getValues(GRAPH_PRECISION), GRAPH_PRECISION, "tmp/part1.png");
  }

  public static Mistakes learn(String csvFilePath, Teacher teacher) {
    List<Data> dataList = teacher.preprocess(csvFilePath);
    List<Rule> L = new LinkedList();

    Rule defaultRule = new Rule(teacher);

    int mistakes = 0;
    int numExamplesSeen = 0;

    Mistakes graph = new Mistakes();

    // Perform the learning algorithm using the dataList and the Teacher object
    for (Data data : dataList) {
      numExamplesSeen++;// for charts

      Rule prediction = existsXinL(L, data);
      FeedbackResult feedback = null;

      if (prediction != null) {
        // predict 'label(exists)' with explanation 'exists'
        feedback = teacher.feedback(data, prediction);

        if (!feedback.getResult()) {
          mistakes++;

          // get correct label and feature
          prediction.add(-feedback.getDiscriminativeFeature());
        }

      } else {
        prediction = defaultRule;
        feedback = teacher.feedback(data, defaultRule);

        if (!feedback.getResult()) {
          mistakes++;

          data.setClassification(feedback.getAnswer());
          Rule rule = new Rule(data);
          L.add(rule);
          rule.add(feedback.getDiscriminativeFeature());
        }
      }

      printIteration(data, prediction.getExplanation(), feedback.getResult());

      double mistakesSoFar = (double) mistakes / (double) numExamplesSeen * 100;
      graph.add(mistakesSoFar);
    }

    // these are for charts also
    System.out.println("Total Mistakes: " + mistakes);
    System.out.println("Mistake Percentage: " + (double) mistakes / dataList.size() * 100 + "%");

    return graph;
  }

  private static void printL(List<Rule> L) {
    for (Rule rule : L) {
      System.out.println(rule.getLabel() + " : " + rule.getConjunction().toString());
    }
  }

  private static void printIteration(Data data, Data prediction, boolean teacherResponse) {
    String predictionStr = prediction == null ? "Default Data" : Arrays.toString(prediction.getAttributes());

    System.out.println("Example: " + Arrays.toString(data.getAttributes()));
    System.out.println("Predicted Label: " + prediction.getClassification());
    System.out.println("Explanation Example: " + predictionStr);
    System.out.println("Teacher Response: " + teacherResponse + "\n");
  }

  // returns a Data object y in L such that x satisfies the conjunction C[y], or
  // null when such an object doesn't exist
  private static Rule existsXinL(List<Rule> L, Data data) {
    for (Rule y : L)
      if (y.holdsTrue(data))
        return y;

    return null;
  }
}