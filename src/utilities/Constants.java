package utilities;

public class Constants {
    public class PlayerConstant {
        // player direction
        public static final int DOWN = 0;
        public static final int UP = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
        // player action
        public static final int HOE = 0;
        public static final int CHOP = 1;
        public static final int WATER = 2;


        public static int getSpriteActionAmount(int player_action, int player_direction) {
            switch (player_action) {
                case HOE, CHOP, WATER -> {
                    switch (player_direction) {
                        case DOWN, UP, LEFT, RIGHT -> {
                            return 2;
                        }
                        default -> {
                            return 1;
                        }
                    }
                }
                default -> {
                    return 1;
                }
            }
        }

        public static int getAction(int player_action, int player_direction) {
            switch (player_action) {
                case HOE -> {
                    switch (player_direction) {
                        case DOWN -> {
                            return 0;
                        }
                        case UP -> {
                            return 1;
                        }
                        case LEFT -> {
                            return 2;
                        }
                        case RIGHT -> {
                            return 3;
                        }
                    }
                }
                case CHOP -> {
                    switch (player_direction){
                        case DOWN -> {
                            return 4;
                        }
                        case UP -> {
                            return 5;
                        }
                        case LEFT -> {
                            return 6;
                        }
                        case RIGHT -> {
                            return 7;
                        }
                    }
                }
                case WATER -> {
                    switch (player_direction){
                        case DOWN -> {
                            return 8;
                        }
                        case UP -> {
                            return 9;
                        }
                        case LEFT -> {
                            return 10;
                        }
                        case RIGHT -> {
                            return 11;
                        }
                    }
                }
                default -> {
                    return 1;
                }
            }
            return 1;
        }


        public static int getSpriteAmount(int player_direction) {
            switch (player_direction) {
                case DOWN, UP, LEFT, RIGHT -> {
                    return 4;
                }
                default -> {
                    return 1;
                }
            }
        }
        public static int getMoving(int player_direction) {
            switch (player_direction) {
                case DOWN -> {
                    return 0;
                }
                case UP -> {
                    return 1;
                }
                case LEFT -> {
                    return 2;
                }
                case RIGHT -> {
                    return 3;
                }

                default -> {
                    return -1;
                }
            }
        }
    }
}
