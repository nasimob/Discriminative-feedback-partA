public class Rule {
  private Conjunction conjunction;
  private String label;
  private Data explanation;

  public Rule(Data data) {
    conjunction = new Conjunction();
    label = data.getClassification();
    explanation = data;
  }

  public Rule(Teacher teacher) {
    explanation = teacher.defaultData();
    label = explanation.getClassification();

    conjunction = new Conjunction();
  }

  public boolean holdsTrue(Data data) {
    return conjunction.isTrue(data.getAttributes());
  }

  public void add(int feature) {
    conjunction.add(feature);
  }

  public String getLabel() {
    return label;
  }

  public Data getExplanation() {
    return explanation;
  }

  public Conjunction getConjunction() {
    return conjunction;
  }
}