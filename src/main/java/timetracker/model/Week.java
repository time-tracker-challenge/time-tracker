package timetracker.model;

import org.chromattic.api.annotations.*;

import java.util.Date;
import java.util.List;

@PrimaryType(name = "tt:week")
public abstract class Week {


  @Name
  public abstract String getName();

  @Property(name = "firstDay")
  public abstract Date getFirstDay();

  public abstract void setFirstDay(Date firstDay);

  @OneToMany
  public abstract List<Task> getTasks();

  @ManyToOne
  public abstract User getParent();

  public abstract void setParent(User user);

}
