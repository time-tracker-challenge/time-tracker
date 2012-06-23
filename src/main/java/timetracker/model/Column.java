package timetracker.model;

import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

@PrimaryType(name = "tt:column")
public abstract class Column
{

  /**
   * Returns the page name.
   * @return the page name
   */
  @Name
  public abstract String getName();

  @ManyToOne
  public abstract Columns getParent();

  public abstract void setParent(Columns columns);

  @Property (name = "title")
  public abstract String getTitle();

  public abstract void setTitle(String title);

  @Property (name = "values")
  public abstract String[] getValues();

  public abstract void setValues(String[] values);


}
