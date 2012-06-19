package timetracker.model;


import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

@PrimaryType(name = "tt:task")
public abstract class Task {

  /**
   * Returns the page name.
   * @return the page name
   */
  @Name
  public abstract String getName();

  @Property(name = "exo:title")
  public abstract String getTitle();

  public abstract void setTitle(String title);

}
