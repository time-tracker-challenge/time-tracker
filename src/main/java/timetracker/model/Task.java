package timetracker.model;


import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

import java.util.List;
import java.util.Map;

@PrimaryType(name = "tt:task")
public abstract class Task
{

  /**
   * Returns the page name.
   * @return the page name
   */
  @Name
  public abstract String getName();

  @ManyToOne
  public abstract Week getParent();

  public abstract void setParent(Week week);

  @Property(name = "comment")
  public abstract String getComment();

  public abstract void setComment(String comment);

  @Property(name="hours")
  public abstract String[] getHours();

  public abstract void setHours(String[] hours);

  @Property(name="columns")
  public abstract String[] getColumns();

  public abstract void setColumns(String[] columns);
}
