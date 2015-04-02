package hkust.cse.calendar.unit;

import hkust.cse.calendar.notificationServices.notificationServices;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Timer;

public class Appt implements Serializable {

	private TimeSpan mTimeSpan;					// Include day, start time and end time of the appointments

	private String mTitle;						// The Title of the appointments

	private String mInfo;						// Store the content of the appointments description

	private int mApptID;						// The appointment id

	private int joinApptID;						// The join appointment id

	private boolean isjoint;					// The appointment is a joint appointment

	private LinkedList<String> attend;			// The Attendant list

	private LinkedList<String> reject;			// The reject list

	private LinkedList<String> waiting;			// The waiting list

	private String location;					// The location

	private String frequency;					// The type of frequency

	private int frequencyAmount;				// The amount of frequency

	private Date originalReminder;
	private Date tempReminder;
	private Date date;
	boolean isReminder;
	private Calendar cal;
	private boolean alive;

	private notificationServices timer;

	public Appt() {								// A default constructor used to set all the attribute to default values
		mApptID = 0;
		mTimeSpan = null;
		mTitle = "Untitled";
		mInfo = "";
		isjoint = false;
		attend = new LinkedList<String>();
		reject = new LinkedList<String>();
		waiting = new LinkedList<String>();
		joinApptID = -1;
		location = "";
		setReminder(0,0,0,0,0);
		isReminder = false;
		alive = true;
	}

	public void setTimer(Date reminder){
		if(isReminder){
			timer = new notificationServices(reminder, mTitle, mInfo, alive);
		}
		else
			return;
	}

	public void resetEveryTimer(){
		if(alive){
			timer.resetTimer(originalReminder);
		}
		else
			return;
	}
	
	public void resetTimer(Date reminder){
		alive = checkTimerLife();
		if(alive){
			timer.resetTimer(reminder);
		}
		else
			return;
	}

	public void setReminder(int year, int month, int day, int hour, int minute){
		originalReminder = new Date(year-1900, month-1, day, hour, minute-10);

		cal = new GregorianCalendar();
		cal.setTime(originalReminder);
		//System.out.println("GregorianCalendar  " + cal.getTime());
		Date d = new Date();
		d = cal.getTime();
		tempReminder = originalReminder;
	}

	public boolean checkTimerLife(){
		alive = timer.checkTimerLife();
		return alive;
	}

	public void setTempReminder(Date date){
		tempReminder = date;
	}

	public Date getTempReminder(){
		return tempReminder;
	}

	public void setIsReminder(boolean r){
		isReminder = r;
	}

	public boolean getIsReminder(){
		return isReminder;
	}

	public Date getReminder(){
		return originalReminder;
	}

	// Getter of the mTimeSpan
	public TimeSpan TimeSpan() {
		return mTimeSpan;
	}

	// Getter of the appointment title
	public String getTitle() {
		return mTitle;
	}

	// Getter of appointment description
	public String getInfo() {
		return mInfo;
	}

	// Getter of the appointment id
	public int getID() {
		return mApptID;
	}

	// Getter of the location
	public String getLocation() {
		return location;
	}

	public String getFrequency() {
		return frequency;
	}

	public int getFrequencyAmount() {
		return frequencyAmount;
	}

	// Getter of the join appointment id
	public int getJoinID(){
		return joinApptID;
	}

	public void setJoinID(int joinID){
		this.joinApptID = joinID;
	}
	// Getter of the attend LinkedList<String>
	public LinkedList<String> getAttendList(){
		return attend;
	}

	// Getter of the reject LinkedList<String>
	public LinkedList<String> getRejectList(){
		return reject;
	}

	// Getter of the waiting LinkedList<String>
	public LinkedList<String> getWaitingList(){
		return waiting;
	}

	public LinkedList<String> getAllPeople(){
		LinkedList<String> allList = new LinkedList<String>();
		allList.addAll(attend);
		allList.addAll(reject);
		allList.addAll(waiting);
		return allList;
	}

	public void addAttendant(String addID){
		if (attend == null)
			attend = new LinkedList<String>();
		attend.add(addID);
	}

	public void addReject(String addID){
		if (reject == null)
			reject = new LinkedList<String>();
		reject.add(addID);
	}

	public void addWaiting(String addID){
		if (waiting == null)
			waiting = new LinkedList<String>();
		waiting.add(addID);
	}

	public void setWaitingList(LinkedList<String> waitingList){
		waiting = waitingList;
	}

	public void setWaitingList(String[] waitingList){
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		if (waitingList !=null){
			for (int a=0; a<waitingList.length; a++){
				tempLinkedList.add(waitingList[a].trim());
			}
		}
		waiting = tempLinkedList;
	}

	public void setRejectList(LinkedList<String> rejectLinkedList) {
		reject = rejectLinkedList;
	}

	public void setRejectList(String[] rejectList){
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		if (rejectList !=null){
			for (int a=0; a<rejectList.length; a++){
				tempLinkedList.add(rejectList[a].trim());
			}
		}
		reject = tempLinkedList;
	}

	public void setAttendList(LinkedList<String> attendLinkedList) {
		attend = attendLinkedList;
	}

	public void setAttendList(String[] attendList){
		LinkedList<String> tempLinkedList = new LinkedList<String>();
		if (attendList !=null){
			for (int a=0; a<attendList.length; a++){
				tempLinkedList.add(attendList[a].trim());
			}
		}
		attend = tempLinkedList;
	}
	// Getter of the appointment title
	public String toString() {
		return mTitle;
	}

	// Setter of the appointment title
	public void setTitle(String t) {
		mTitle = t;
	}

	// Setter of the appointment description
	public void setInfo(String in) {
		mInfo = in;
	}

	// Setter of the mTimeSpan
	public void setTimeSpan(TimeSpan d) {
		mTimeSpan = d;
	}

	// Setter if the appointment id
	public void setID(int id) {
		mApptID = id;
	}

	// Setter of the location
	public void setLocation(String location) {
		this.location= location;
	}

	// Setter of frequency
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	// Setter of frequency amount
	public void setFrequencyAmount(int frequencyAmount) {
		this.frequencyAmount = frequencyAmount;
	}

	// check whether this is a joint appointment
	public boolean isJoint(){
		return isjoint;
	}

	// setter of the isJoint
	public void setJoint(boolean isjoint){
		this.isjoint = isjoint;
	}



}
