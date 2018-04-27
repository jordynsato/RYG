import java.util.Scanner;

//Red range: 0-2
//Yellow range: 3-7
//Green range: 8-10

public class Driver {

	//Conversion to RYG scale
	public static String toRYG(double standard) {
		//Yellow 50 = 5
		int r = (int)Math.round(25.35*standard + 20.8) + 2;//300/(Math.exp(0.5*standard)+1);
		//25.35, 20.8: Y48
		//25.55, 9.98: Y38
		//0.417446, -6.20471, 49.4238, -6.95924: Y 37
		//25.5522, 9.98446: Y 38
		//25.6815, 12.4277: Y 41
		
		System.out.println(r);
		
		String letter = "";
		if(r <= 100) {
			letter = "R";
		}
		else if(r <= 200) {
			letter = "Y";
			r -= 100;
		}
		else {
			letter = "G";
			r -= 200;
		}
		
		return letter + " " + r;
	}
	
	//Conversion to standard scale
	public static String toStandard(String RYG) {
		String[] input = RYG.split(" ");
		double d = 0;
		double i = new Double(input[1]);
		switch(input[0]) {
			//Split the red into 4 parts for 0-3, does it unevenly
			case "R":
				if(i <= 33.33)
					d = 0;
				else if(i <= 66.67)
					d = 1;
				else if(i <= 85)
					d = 2;
				else
					d = 3;
				break;
			//Split the yellow into 5 uneven parts
			case "Y":
				if(i <= 20)
					d = 3.5;
				else if(i <= 40)
					d = 4;
				else if(i <= 60)
					d = 5;
				else if(i <= 80)
					d = 6;
				else
					d = 7;
				break;
			//Split the green into 3 even parts
			case "G":
				if(i <= 33.33)
					d = 8;
				else if(i <= 66.67)
					d = 9;
				else
					d = 10;
				break;
			default:
				//do nothing
				break;
		}
		//returns the rating as a String
		return Double.toString(d);
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean rerunFlag = true;
		System.out.println("Welcome to the RYG Convertor!");
		int option = 0;
		
		do {
			//Checking for which convertor to use
			boolean rerunOpt = true;
			do {
				try {
					System.out.println("Would you like to convert from (1) RYG to Standard or (2) Standard to RYG?");
					if(scan.hasNextLine()) {
						option = scan.nextInt();
						if(option != 1 && option != 2) {
							System.out.println("Please choose 1 or 2.");
							scan.nextLine();
							continue;
						}
					}
				} catch(Exception e) {
					System.out.println("Please enter in a valid integer.");
					scan.nextLine();
					continue;
				}
				rerunOpt = false;
			} while(rerunOpt);
			scan.nextLine();
			//Run whatever method needed to do conversion
			//toStandard conversion
			if(option == 1) {
				boolean rerunStandard = true;
				String c = "";
				do {
					try {
						System.out.println("Please enter 'R', 'Y', or 'G':");
						if(scan.hasNextLine()) {
							c = scan.nextLine();
							if(!c.equalsIgnoreCase("R") && !c.equalsIgnoreCase("Y") && !c.equalsIgnoreCase("G")) {
								System.out.println("Please enter in 'R', 'Y', or 'G'.");
								continue;
							}
						}
					} catch(Exception e) {
						System.out.println("Please enter in a valid rating.");
						scan.nextLine();
						continue;
					}
					rerunStandard = false;
				} while(rerunStandard);
				
				double stand = 0;
				boolean rerunNum = true;
				do {
					try {
						System.out.println("Please enter the rating:");
						if(scan.hasNextLine()) {
							stand = scan.nextDouble();
							if(stand < 0 || stand > 100) {
								System.out.println("Please enter in a valid rating.");
								scan.nextLine();
								continue;
							}
						}
					} catch(Exception e) {
						System.out.println("Please enter in a valid rating.");
						scan.nextLine();
						continue;
					}
					rerunNum = false;
				} while(rerunNum);
				
				String RYG = c.toUpperCase() + " " + stand;
				System.out.println("The standard rating of " + RYG + " is " + toStandard(RYG));
			}
			//toRYG conversion
			else {
				boolean rerunRYG = true;
				double stand = 0;
				do {
					try {
						System.out.println("Please enter the standard rating:");
						if(scan.hasNextLine()) {
							stand = scan.nextDouble();
							if(stand < 0 || stand > 10) {
								System.out.println("Please enter in a valid standard rating.");
								scan.nextLine();
								continue;
							}
						}
					} catch(Exception e) {
						System.out.println("Please enter in a valid standard rating.");
						scan.nextLine();
						continue;
					}
					rerunRYG = false;
				} while(rerunRYG);
				System.out.println("The RYG rating of " + stand + " is " + toRYG(stand));
			}
			
			System.out.println("Would you like to rerun the program? (Y/N)");
			String response = scan.next();
			if(!response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("YES")) {
				rerunFlag = false;
				System.out.println("Thank you for using the convertor!");
			}
		} while(rerunFlag);
		scan.close();
	}

}
