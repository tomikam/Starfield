import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Starfield extends PApplet {

Particle swarm[];
int swarmLength = 1000;
int speedMod = 10;
float accelMod = 0.1f;

//your code here
public void setup()
{
	//your code here
	size(700, 700);
	swarm = new Particle[swarmLength];
	swarm[0] = new OddballParticle();
	swarm[1] = new Jumbo();
	for (int i = 2; i < swarmLength; i ++) {
		swarm[i] = new NormalParticle();
	}
	
	//swarm[50].myColor = color(255, 0, 0);
}
public void draw()
{
	//your code here
	background(0);
	fill(255);
	text("speedMod = " + speedMod, 10, 10);
	text("accelMod =  " + accelMod, 10, 20);
	for (int i = 0; i < swarmLength; i ++) {
		swarm[i].move();
		swarm[i].show();
	}
}
public void mousePressed() {
	if (mouseButton  == LEFT) {
		((OddballParticle)swarm[0]).myX = /*Math.random()*width*/width/8;
		((OddballParticle)swarm[0]).myY = /*Math.random()*height*/width/2;
		((OddballParticle)swarm[0]).mySpeed = Math.random()*speedMod;
		((OddballParticle)swarm[0]).myAngle = Math.random()* (2*PI);
		for (int i = 1; i < swarmLength; i ++) {
			((NormalParticle)swarm[i]).myX = width/2;
			((NormalParticle)swarm[i]).myY = height/2;
			((NormalParticle)swarm[i]).mySpeed = Math.random()*speedMod;
			((NormalParticle)swarm[i]).myAngle = Math.random()* (2*PI);
		}
	}
}
public void keyPressed() {
	if (key == 'q' || key == 'Q') {
		speedMod +=1;
	} else if ((key == 'a' || key == 'A') && speedMod > 1) {
		speedMod -= 1;
	} else if (key == 'w' || key == 'W') {
		accelMod += 0.1f;
	} else if (key == 's' || key == 'S') {
		accelMod -= 0.1f;
	}
}
class NormalParticle implements Particle
{
	double myX, myY, myAngle, mySpeed;
	int mySize;
	int myColor;
	NormalParticle() {
		myX = /*Math.random()*width*/width/2;
		myY = /*Math.random()*height*/height/2;
		myAngle = Math.random()* (2*PI);
		mySpeed = Math.random()*speedMod;
		myColor = color(155, 200, 120);
		mySize = 5;
	}
	public void move() {
		myX = myX + Math.cos((float)myAngle)*mySpeed;
		myY = myY + Math.sin((float)myAngle)*mySpeed;
		float myDist = dist((float)myX, (float)myY, width/2, height/2);
		if (myDist > 200) {
			mySpeed += accelMod;
		} else if (myDist < 200) {
			mySpeed -= accelMod;
		} else if (myDist > 400) {
			mySpeed -= accelMod;
		}
	}
	public void show() {
		fill(myColor);
		noStroke();
		ellipse((float)myX, (float)myY, mySize, mySize);
	}
	//your code here
}
interface Particle
{
	//your code here
	public void move();
	public void show();
}
class OddballParticle implements Particle
{
	//your code here
	double myX, myY, mySpeed, myAngle;
	int myColor;
	OddballParticle() {
		myX = /*Math.random()**/width/8;
		myY = /*Math.random()*height/8*/height/2;
		myAngle = Math.random()* (2*(Math.PI));
		mySpeed = Math.random()*speedMod;
		myColor = color(255, 0, 0);
	}
	public void move() {
		myAngle += (Math.PI)/32;
		float myDist = dist((float)myX, (float)myY, width/2, height/2);

		myX = myX + Math.cos(myDist)*mySpeed;
		myY = myY + Math.sin(myDist)*mySpeed;

		
		
		/*if (myDist > 200) {
			myX = myX - Math.cos(myDist)*mySpeed;
			myY = myY - Math.cos(myDist)*mySpeed;

			mySpeed += accelMod;
		} else if (myDist < 200) {
			mySpeed -= accelMod;
		} else if (myDist > 400) {
			mySpeed -= accelMod;
		}*/
	}
	public void show() {
		pushMatrix();
		translate(width/2, height/2);
		rotate((float)myAngle);
		
			fill(myColor);
			noStroke();
			ellipse((float)myX, (float)myY, 20, 20);

		popMatrix();
	}
}
class Jumbo extends NormalParticle
{
	//int mySize;
	Jumbo()
	{
		mySize = 25;
	}
}

//TESTING!!!

//Particle mySize?
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Starfield" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
