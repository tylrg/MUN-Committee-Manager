public class Clock
{
    //INSTANCE FIELDS
    private int hour;
    private int minute;
    private int second;
    //SPECIFIC CONSTRUCTOR
    public Clock(int h,int m, int s)
    {
        hour=h;
        minute=m;
        second=s;
    }
    public Clock(int m, int s)
    {
        
        minute=m;
        second=s;
    }
    public Clock(int s)
    {
        minute = s/60;
        second = s%60;
    }
    //CUSTOM METHODS
    public String displayGregorianTime()
    {
        if ((minute<10)&&(second<10))
        {
            return hour+":0"+minute+":0"+second;
        }
        else if (minute<10)
        {
            return hour+":0"+minute+":"+second;
        }
        else if (second<10)
        {
            return hour+":"+minute+":0"+second;
        }
        return hour+":"+minute+":"+second;
    }

    public String displayTimerTime()
    {
        if(second<10)
        {
            return minute+":0"+second;
        }
        return minute+":"+second;
    }

    public void tickHour()
    {
        hour++;
        if(hour==13)
            hour=1;
    }

    public void tickMinute()
    {
        minute++;
        if(minute==60)
        {
            minute=0;
            tickHour();
        }

    }

    public void tickSecond()
    {
        second++;
        if(second==60)
        {
            second=0;
            tickMinute();
        }
    }

    public void decSecond()
    {
        if(second==0)
        {
            second=59;
            decMinute();
        }
        else{
            second--;
        }
    }

    public void decMinute()
    {
        if(minute>0)
        {
            minute--;
        }
    }
    //GET METHODS

    public int getHour(){
        return hour;
    }

    public int getMinute(){
        return minute;
    }

    public int getSecond(){
        return second;
    }
    //SET METHODS
    public void setHour(int h){
        hour=h;
    }

    public void setMinute(int m){
         minute=m;
    }

    public void setSecond(int s){
        second=s;
    }
    
    public int getTotalSeconds()
    {
        return (minute*60) + second;

    }
}//END CLASS
