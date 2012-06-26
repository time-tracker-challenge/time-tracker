package timetracker.bean;

public class TaskRow {
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
