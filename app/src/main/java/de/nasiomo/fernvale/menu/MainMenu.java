package de.nasiomo.fernvale.menu;

public class MainMenu {
    private String title = "FERNVALE";
    private String[] options = {"Start Game", "Options", "Exit"};
    private int selectedOption = 0;

    public MainMenu() {
    }

    public void update() {
        // Input handling can be added here
        // For now, just hold the menu state
    }

    public void render() {
        // The gray background is set in Window.glClearColor()
        // Further UI rendering (text, buttons) would be implemented here
        // For now, the gray background alone is displayed
    }

    public String getTitle() {
        return title;
    }

    public String[] getOptions() {
        return options;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int index) {
        if (index >= 0 && index < options.length) {
            selectedOption = index;
        }
    }
}
