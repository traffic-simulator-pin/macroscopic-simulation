package br.udesc.pinii.macro.util;

import java.util.Random;

public class Params {

    public static float CAPACITY_FACTOR = 1.0f;
    public static int NUM_EPISODES = 150;
    public static int NUM_STEPS = 100;
    public static int STEP = 0;
    public static int EPISODE = 0;
    public static final long EXPERIMENT_ID = 1;
    public static Random RANDOM = new Random(EXPERIMENT_ID);
    public static int DEMAND_SIZE = 0;
    public static int CORES = Runtime.getRuntime().availableProcessors();
    public static float EG_EPSILON_DEFAULT = 1.0f;
    public static float EG_EPSILON;
    public static float EG_DECAYRATE = 0.92f;
    public static float PHI_MSA = 0.5f;

}
