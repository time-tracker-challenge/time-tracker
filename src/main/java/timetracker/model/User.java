package timetracker.model;

import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;
import org.chromattic.ext.ntdef.NTFolder;

import java.util.Collection;
import java.util.List;


@PrimaryType(name = "tt:user")
public abstract class User extends NTFolder
{

  @Name
  public abstract String getName();

  @Property(name = "username")
  public abstract String getUsername();

  public abstract void setUsername(String username);

  @OneToMany
  public abstract List<Week> getWeeks();


}
