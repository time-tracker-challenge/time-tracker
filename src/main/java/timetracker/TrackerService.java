package timetracker;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticSession;
import org.chromattic.ext.ntdef.NTFolder;
import org.exoplatform.portal.webui.util.Util;
import timetracker.model.Task;
import timetracker.model.User;
import timetracker.model.Week;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TrackerService {

  Chromattic chromattic_;

  public void initChromattic(Chromattic chromattic)
  {
    chromattic_ = chromattic;
    createApplicationData();
  }

  private void createApplicationData()
  {
    ChromatticSession session = chromattic_.openSession();
    try
    {
      NTFolder users = session.findByPath(NTFolder.class, "users");
      if (users==null)
      {
        users = session.insert(NTFolder.class, "users");
        session.save();
      }

      NTFolder columns = session.findByPath(NTFolder.class, "columns");
      if (columns==null)
      {
        columns = session.insert(NTFolder.class, "columns");
        session.save();
      }

    }
    finally
    {
      session.close();
    }

  }


  public String[] getColumns()
  {
    return new String[] {"Client", "Project", "Task"};
  }

  public List<Task> getTasks(Calendar fromDate)
  {
    String username = Util.getPortalRequestContext().getRemoteUser();
    if (username!=null)
    {
      ChromatticSession session = chromattic_.openSession();
      try
      {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String path = "users/"+username+"/"+df.format(fromDate.getTime());
        System.out.println("path::"+path);
        Week week = session.findByPath(Week.class, path);
        if (week!=null)
        {
          return week.getTasks();
        }
      }
      finally
      {
        session.close();
      }
    }

    return null;
  }

  public void updateData(Calendar fromDate, String data)
  {
    fromDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    String username = Util.getPortalRequestContext().getRemoteUser();
    if (username!=null)
    {
      ChromatticSession session = chromattic_.openSession();
      try
      {
        NTFolder users = session.findByPath(NTFolder.class, "users");
        User user = session.findByPath(User.class, "users/"+username);
        if (user==null)
        {
          user = session.insert(User.class, username);
          users.addChild(user);
          user.setUsername(username);
        }

        String weekName = df.format(fromDate.getTime());
        Week week = session.findByPath(Week.class, "users/"+username+"/"+weekName);
        if (week!=null)
        {
          week.setParent(null);
        }

        week = session.insert(Week.class, weekName);
        week.setParent(user);
        week.setFirstDay(fromDate.getTime());


        String[] rows = data.split(";");
        for (int ir=0 ; ir<rows.length ; ir++)
        {
          String[] entries = rows[ir].split("=");
          String[] columns = entries[0].split("-");
          String[] hours = entries[1].split("-");
          String taskName = columns[0]+"-"+columns[1]+"-"+columns[2];
          Task task = session.create(Task.class, taskName);
          task.setParent(week);
          task.setComment("");
          task.setHours(hours);
          task.setColumns(columns);
        }

        session.save();

      }
      finally
      {
        session.close();
      }
    }
  }


  public void createDummyData()
  {
    String username = Util.getPortalRequestContext().getRemoteUser();
    if (username!=null)
    {
      ChromatticSession session = chromattic_.openSession();
      try
      {
        NTFolder users = session.findByPath(NTFolder.class, "users");
        User user = session.findByPath(User.class, "users/"+username);
        if (user!=null)
        {
          session.remove(user);
          session.save();
        }
        System.out.println("CREATING USER DATA FOR : "+username);
        user = session.insert(User.class, username);
        users.addChild(user);
        user.setUsername(username);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for (int i=1 ; i<=3 ; i++)
        {
          String name = df.format(c.getTime());

          Week week1 = session.create(Week.class, name);
          week1.setParent(user);
          week1.setFirstDay(c.getTime());

          if (i%2!=0)
          {
            Task task = session.create(Task.class, "eXo-Presales-Weka");
            task.setParent(week1);
            task.setComment("working on a poc");
            task.setHours(new String[] {"4", "0", "0", "4", "2"});
            task.setColumns(new String[]{"eXo", "Presales", "Weka"});
          }

          if (i%3!=0)
          {
            Task task2 = session.create(Task.class, "eXo-Product-Juzu");
            task2.setParent(week1);
            task2.setComment("time tracker portlet");
            task2.setHours(new String[] {"4", "8", "8", "4", "6"});
            task2.setColumns(new String[]{"eXo", "Product", "Juzu"});
          }

          if (i%5!=0)
          {
            Task task3 = session.create(Task.class, "eXo-Product-Spec");
            task3.setParent(week1);
            task3.setComment("In Place Editing");
            task3.setHours(new String[] {"2", "2", "0", "6", "6"});
            task3.setColumns(new String[]{"eXo", "Product", "Spec"});
          }

          //removing one week
          c.add(Calendar.DATE, -7);

        }

        session.save();
      }
      finally
      {
        session.close();
      }
    }

  }




}
