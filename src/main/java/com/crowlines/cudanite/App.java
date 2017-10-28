package com.crowlines.cudanite;

public class App {

	private static final int OPT_CN_BLOCKS = 0;
	private static final int OPT_CN_THREADS = 8;

	private static final int DEFAULT_BFACTOR = 6;
	private static final int DEFAULT_BSLEEP = 25;

	private int[][] device_config = new int[8][2];

	private int[] device_bfactor = new int[8];
	private int[] device_bsleep = new int[8];

	public App() {
		for (int i = 0; i < 8; i++) {
			device_config[i][0] = OPT_CN_BLOCKS;
			device_config[i][1] = OPT_CN_THREADS;
			device_bfactor[i] = DEFAULT_BFACTOR;
			device_bsleep[i] = DEFAULT_BSLEEP;
		}

		// PARSE CMDLINE

		// create user:password

		JCudaDeviceHelper info = new JCudaDeviceHelper();
	}

	public static void main(String[] args) {
		App app = new App();
	}

}
