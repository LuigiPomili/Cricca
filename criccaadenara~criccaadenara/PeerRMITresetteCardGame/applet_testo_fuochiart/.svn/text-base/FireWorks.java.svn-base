import java.awt.*;
import java.applet.Applet;

public class FireWorks extends Applet implements Runnable
{
  Dimension	d;
  Font 		font = new Font("Helvetica", Font.BOLD, 36);
  FontMetrics	fm;  
  Graphics	goff;
  Image		ii;
  Thread	thethread;
  String	s;

  final int	numrockets=8;
  final int	num=16;
  int[]		xcoord, ycoord, xspeed, yspeed;
  int[]		count;
  boolean[]	exploding;

  public String getAppletInfo()
  {
    return("Winner Applet - Cricca a Denara Project");
  }

  public void init()
  {
    Graphics g;
    int i;
    d = size();
    g=getGraphics();
    g.setFont(font);
    fm = g.getFontMetrics();
    xcoord=new int[numrockets*num];
    ycoord=new int[numrockets*num];
    xspeed=new int[numrockets*num];
    yspeed=new int[numrockets*num];
    count=new int[numrockets];
    exploding=new boolean[numrockets];

    for (i=0; i<numrockets*num; i++)
    {
      xcoord[i]=-20000;
      ycoord[i]=-20000;
      xspeed[i]=0;
      yspeed[i]=0;
    }
    for (i=0; i<numrockets; i++)
    {
      count[i]=1+i*16;
      exploding[i]=true;
    }
    s=getParameter("Text");
  }

  public void paint(Graphics g)
  {
    Graphics gg;

    if (goff==null && d.width>0 && d.height>0)
    {
      ii = createImage(d.width, d.height);
      goff = ii.getGraphics();
      goff.setFont(font);
    }
    if (goff==null || ii==null)
      return;

    goff.setColor(Color.black);
    goff.fillRect(0, 0, d.width, d.height);
    FireWorks(goff);
    goff.setColor(new Color(128, 192, 255));
    goff.drawString(s,(d.width-fm.stringWidth(s)) / 2, d.height/2 );
    g.drawImage(ii, 0, 0, this);
  }


  public void FireWorks(Graphics g)
  {
    int		i,j,index;
    int		x,y,xspd,yspd;

    for (i=0; i<numrockets; i++)
    {
      if (!exploding[i] && yspeed[i*num]>0)
      { // explode
        exploding[i]=true;
        for (j=0; j<num; j++)
        {
          index=i*num+j;
          yspeed[index]=(int)((Math.random()*28.0))-15;
          xspeed[index]=(int)((Math.random()*31.0))-16;
          if (xspeed[index]>=0) xspeed[index]++;
        }
      }
      for (j=0; j<num; j++)
      {
        index=i*num+j;
        if (exploding[i])
        {
          switch(i&3)
          {
            case 0:
              g.setColor(new Color(192,(count[i])+32,(count[i])+127));
              break;
            case 1:
              g.setColor(new Color(count[i]+32,192,count[i]+127));
              break;
            case 2:
              g.setColor(new Color(192, 192, count[i]+32));
              break;
            default:
              g.setColor(new Color(count[i]+32, count[i]+127, 192));
          }
        }
        else
          g.setColor(Color.white);
//        g.drawLine(xcoord[index]>>3,ycoord[index]>>3,xcoord[index]>>3,ycoord[index]>>3);
        g.fillRect(xcoord[index]>>3, ycoord[index]>>3,2,2);
        xcoord[index]+=xspeed[index];
//        if (xcoord[index]<0)
//          xcoord[index]+=(d.width<<3);
//        if (xcoord[index]>(d.width<<3))
//         xcoord[index]-=(d.width<<3);
        ycoord[index]+=yspeed[index];
        yspeed[index]++;
      }
      count[i]--;
      if (count[i]<=0)
      {
        count[i]=128;
        exploding[i]=false;
//        x=((int)((Math.random()*(d.width/2)))+(d.width/4))<<3;
        x=((int)((Math.random()*d.width)))<<3;
        y=d.height<<3;
        yspd=(int)((Math.random()*28))-108;
        xspd=(int)((Math.random()*15.0))-8;
          if (xspd>=0) xspd++;
        for (j=0; j<num; j++)
        {
          index=i*num+j;
          xcoord[index]=x;
          ycoord[index]=y;
          xspeed[index]=xspd;
          yspeed[index]=yspd;
        }
      }
    }
  }


  public void run()
  {
    long	starttime;
    Graphics	g;

    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    g=getGraphics();

    while(true)
    {
       starttime=System.currentTimeMillis();
      try
      {
        paint(g);
        starttime += 20;
        Thread.sleep(Math.max(0, starttime-System.currentTimeMillis()));
//        Thread.sleep(20);
      }
      catch (InterruptedException e)
      {
        break;
      }
    }
  }

  public void start()
  {
    if (thethread == null) {
      thethread = new Thread(this);
      thethread.start();
    }
  }

  public void stop()
  {
    if (thethread != null) {
      thethread.stop();
      thethread = null;
    }
  }
}
