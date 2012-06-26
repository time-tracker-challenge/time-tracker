package timetracker.bean;

public class Row {
  String[] columns;
  String [] hours;

  public String[] getColumns() {
    return columns;
  }

  public void setColumns(String[] columns) {
    this.columns = columns;
  }

  public String[] getHours() {
    return hours;
  }

  public void setHours(String[] hours) {
    this.hours = hours;
  }
}
