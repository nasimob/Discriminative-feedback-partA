class FeedbackResult {
  private boolean result;
  private String answer;
  private int discriminativeFeature;

  public FeedbackResult(boolean result, String answer, int discriminativeFeature) {
    this.result = result;
    this.answer = answer;
    this.discriminativeFeature = discriminativeFeature;
  }

  public boolean getResult() {
    return result;
  }

  public String getAnswer() {
    return answer;
  }

  public int getDiscriminativeFeature() {
    return discriminativeFeature;
  }
}