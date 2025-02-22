package cosi131;

import java.util.Scanner;

public class BankerMain {

	// Key facts
	int n = 5; // Number of processes
	// int m = 3; // Number of resources ORIGINAL
	int m = 4; // Number of resources ORIGINAL

	int need[][] = new int[n][m];
	int[][] max, max1, max2, max3, max4;
	int[][] alloc, alloc1, alloc3, alloc4;
	int[] avail, avail1, avail2, avail3, avail4;
	int safeSequence[] = new int[n];
	Scanner scanner = new Scanner(System.in);

	void initializeValues() {
		for (int i = 0; i < n; i++)
			safeSequence[i] = -1;
		// P0, P1, P2, P3, P4 are the Process names here
		// ORIGINAL Allocation Matrix
		alloc3 = new int[][] { { 0, 1, 0 }, // P0
				{ 2, 0, 0 }, // P1
				{ 3, 0, 2 }, // P2
				{ 2, 1, 1 }, // P3
				{ 0, 0, 2 } }; // P4

		alloc1 = new int[][] { { 0, 1, 0 }, { 3, 0, 2 }, { 3, 0, 2 }, { 2, 1, 1 }, { 3, 3, 0 } };
		alloc = new int[][] { { 3, 0, 1, 4 }, { 2, 2, 1, 0 }, { 3, 1, 2, 1 }, { 0, 5, 1, 0 }, { 5, 2, 1, 2 } };

		// ORIGINAL MAX Matrix
		max3 = new int[][] { { 7, 5, 3 }, // P0
				{ 3, 2, 2 }, // P1
				{ 3, 2, 2 }, // P2
				{ 2, 2, 2 }, // P3
				{ 4, 3, 3 } }; // P4

		max2 = new int[][] { { 7, 5, 3 }, // P0
				{ 3, 2, 2 }, // P1
				{ 9, 0, 2 }, // P2
				{ 2, 2, 2 }, // P3
				{ 4, 3, 3 } }; // P4

		max1 = new int[][] { { 7, 5, 3 }, { 3, 2, 2 }, { 9, 0, 2 }, { 2, 2, 2 }, { 4, 3, 3 } };
		max = new int[][] { { 5, 1, 1, 7 }, { 3, 2, 1, 1 }, { 3, 3, 2, 1 }, { 4, 6, 1, 2 }, { 6, 3, 2, 5 } };

		// Available Resources
		avail2 = new int[] { 3, 3, 2 }; // Original
		avail1 = new int[] { 2, 2, 2 };
		avail3 = new int[] { 0, 0, 0 };
		avail4 = new int[] { 0, 3, 0, 1 };
		avail = new int[] { 1, 0, 0, 2 };

	}

	void isSafe() {
		int count = 0;

		// visited array to find the already allocated process
		boolean visited[] = new boolean[n];
		for (int i = 0; i < n; i++) {
			visited[i] = false;
		}

		// work array to store the copy of available resources
		int work[] = new int[m];
		for (int i = 0; i < m; i++) {
			work[i] = avail[i];
		}

		// Iterate through all processes
		while (count < n) {
			boolean flag = false;

			// All pairs of processes will be compared
			for (int i = 0; i < n; i++) {
				if (visited[i] == false) {
					int j;
					for (j = 0; j < m; j++) {
						if (need[i][j] > work[j])
							break;
					}
					if (j == m) {
						safeSequence[count++] = i;
						visited[i] = true;
						flag = true;

						for (j = 0; j < m; j++) {
							work[j] = work[j] + alloc[i][j];
						}
						printSafeStatus(work);

					}
				}
			}
			if (flag == false) {
				break;
			}
		}
		if (count < n) {
			System.out.println("The System is UnSafe!");
		} else {
			printSafeSequence();
		}
	}

	private void printSafeSequence() {
		System.out.println("Following is the SAFE Sequence");
		for (int i = 0; i < n; i++) {
			System.out.print("P" + safeSequence[i]);
			if (i != n - 1)
				System.out.print(" -> ");
		}
		System.out.println();
	}

	void calculateNeed() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				need[i][j] = max[i][j] - alloc[i][j];
			}
		}
	}

	String safeStatus(int pr) {
		String retval = "";
		for (int i = 0; i < n; i++) {
			if (safeSequence[i] == pr) {
				retval += i;
				break;
			}
		}
		return retval;
	}

	void printSafeStatus(int[] work) {
		printStatus();
		printAvailableResources(work);
		System.out.print("\nSpace to continue: ");
		String userInput = scanner.nextLine();
	}

	private void printAvailableResources(int[] work) {
		System.out.println("\n--- Available --");
		System.out.printf("|%4s|%4s|%4s|\n", "A ", "B ", "C ", "D ");
		System.out.println("----------------");
		System.out.printf("|%4d|%4d|%4d|%4d|\n", work[0], work[1], work[2], work[3]);
		System.out.println("----------------");

	}

	void printStatus() {
		System.out.println("--------------------------------------------");
		System.out.printf("%-3s|%12s|%12s|%10s|\n", "Proc", " Allocation ", "Max     ", "   Need     ");
		System.out.printf("    |%4s%4s%4s%4s|", "A", "B", "C", "D");
		System.out.printf("%4s%4s%4s%4s|", " A", " B", " C", "D");
		System.out.printf("%4s%4s%4s%4s|\n", " A", " B", " C", " D");
		System.out.println("--------------------------------------------");
		for (int i = 0; i < 5; i++) {
			System.out.printf("%-4s|%4d%4d%4d%4d|", "P" + i, alloc[i][0], alloc[i][1], alloc[i][2],alloc[i][3]);
			System.out.printf("%4d%4d%4d%4d|", max[i][0], max[i][1], max[i][2], max[i][3]);
			System.out.printf("%4d%4d%4d%4d|", max[i][0] - alloc[i][0], max[i][1] - alloc[i][1], max[i][2] - alloc[i][2],
					max[i][3] - alloc[i][3]);
			System.out.printf("     %s\n", safeStatus(i));
		}
		System.out.println("--------------------------------------------");
	}

	public static void main(String[] args) {
		int i, j, k;
		BankerMain bnker = new BankerMain();

		bnker.initializeValues();
		bnker.calculateNeed();

		bnker.printSafeStatus(bnker.avail);

		// Check whether system is in safe state or not
		bnker.isSafe();
	}
}
