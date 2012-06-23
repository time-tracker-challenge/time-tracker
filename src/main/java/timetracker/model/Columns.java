package timetracker.model;


import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;

import java.util.List;

@PrimaryType(name = "tt:columns")
public abstract class Columns {

  @Name
  public abstract String getName();

  @OneToMany
  public abstract List<Column> getChildren();

}
