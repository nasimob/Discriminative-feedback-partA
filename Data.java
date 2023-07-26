class Data {
  private final int id;
  private final boolean[] attributes;
  private String classification; // empty classification is null

  public Data(int id, boolean[] attributes, String classification) {
    this.id = id;
    this.attributes = attributes;
    this.classification = classification;
  }

  // creating empty Data - i.e same Data object without the classification
  public Data(Data data) {
    this.id = data.id;
    this.attributes = data.getAttributes();
    this.classification = null;
  }

  public int getID() {
    return id;
  }

  public boolean[] getAttributes() {
    return attributes;
  }

  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }
}