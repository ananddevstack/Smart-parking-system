import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Vehicle {
    String ownerName;
    String mobileNumber;
    String vehicleNumber;
    String vehicleType;
    String parkingDate;
    int duration;
    double fee;

    Vehicle(String ownerName, String mobileNumber, String vehicleNumber, String vehicleType, String parkingDate, int duration, double fee) {
        this.ownerName = ownerName;
        this.mobileNumber = mobileNumber;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.parkingDate = parkingDate;
        this.duration = duration;
        this.fee = fee;
    }
}

public class CarParkingSystem {
    private static List<Vehicle> parkedVehicles = new ArrayList<>();
    private static HashMap<String, String> admins = new HashMap<>();
    private static HashMap<String, String> users = new HashMap<>();
    private static int availableSlots = 50;
    private static double monthlyIncome = 0.0;
    private static HashMap<Integer, Vehicle> slots = new HashMap<>();

    public static void main(String[] args) {
        admins.put("admin", "admin123"); // Predefined admin credentials

        JFrame frame = new JFrame("Car Parking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel loginPanel = new JPanel(new GridLayout(5, 2));

        JLabel roleLabel = new JLabel("Login as:");
        String[] roles = {"Admin", "User   "};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register (User)");

        loginPanel.add(roleLabel);
        loginPanel.add(roleComboBox);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        frame.add(loginPanel);

        loginButton.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (role.equals("Admin")) {
                if (admins.containsKey(username) && admins.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Admin Login Successful!");
                    showAdminPanel(frame);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Admin Credentials!");
                }
            } else if (role.equals("User   ")) {
                if (users.containsKey(username) && users.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(frame, "User Login Successful!");
                    showUserPanel(frame);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid User Credentials!");
                }
            }
        });

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!users.containsKey(username)) {
                users.put(username, password);
                JOptionPane.showMessageDialog(frame, "User Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists!");
            }
        });

        frame.setVisible(true);
    }

    private static void showAdminPanel(JFrame frame) {
        frame.getContentPane().removeAll();

        JPanel adminPanel = new JPanel(new GridLayout(4, 1));
        JButton viewAllButton = new JButton("View All Parked Vehicles");
        JButton showIncomeButton = new JButton("Show Monthly Income");
        JButton findVehicleButton = new JButton("Find Vehicle by Number");
        JButton backButton = new JButton("Back");

        adminPanel.add(viewAllButton);
        adminPanel.add(showIncomeButton);
        adminPanel.add(findVehicleButton);
        adminPanel.add(backButton);

        frame.add(adminPanel);
        frame.revalidate();
        frame.repaint();

        viewAllButton.addActionListener(e -> {
            StringBuilder allVehicles = new StringBuilder("All Parked Vehicles:\n\n");
            int slot = 1;

            for (Vehicle v : parkedVehicles) {
                allVehicles.append("Slot ").append(slot).append(":\n");
                allVehicles.append("  Owner: ").append(v.ownerName).append("\n");
                allVehicles.append("  Mobile: ").append(v.mobileNumber).append("\n");
                allVehicles.append("  Vehicle Number: ").append(v.vehicleNumber).append("\n");
                allVehicles.append("  Type: ").append(v.vehicleType).append("\n");
                allVehicles.append("  Date: ").append(v.parkingDate).append("\n");
                allVehicles.append("  Duration: ").append(v.duration).append(" hours\n");
                allVehicles.append("  Fee: ").append(v.fee).append("\n\n");
                slot++;
            }

            if (parkedVehicles.isEmpty()) {
                allVehicles.append("No vehicles are currently parked.");
            }

            JOptionPane.showMessageDialog(frame, allVehicles.toString());
        });

        showIncomeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Monthly Income: " + monthlyIncome);
        });

        findVehicleButton.addActionListener(e -> {
            String vehicleNumber = JOptionPane.showInputDialog(frame, "Enter Vehicle Number:");
            if (vehicleNumber != null && !vehicleNumber.trim().isEmpty()) {
                Vehicle foundVehicle = null;
                for (Vehicle v : parkedVehicles) {
                    if (v.vehicleNumber.equalsIgnoreCase(vehicleNumber.trim())) {
                        foundVehicle = v;
                        break;
                    }
                }
                if (foundVehicle != null) {
                    StringBuilder vehicleDetails = new StringBuilder("Vehicle Details:\n\n");
                    vehicleDetails.append("  Owner: ").append(foundVehicle.ownerName).append("\n");
                    vehicleDetails.append("  Mobile: ").append(foundVehicle.mobileNumber).append("\n");
                    vehicleDetails.append("  Vehicle Number: ").append(foundVehicle.vehicleNumber).append("\n");
                    vehicleDetails.append("  Type: ").append(foundVehicle.vehicleType).append("\n");
                    vehicleDetails.append("  Date: ").append(foundVehicle.parkingDate).append("\n");
                    vehicleDetails.append("  Duration: ").append(foundVehicle.duration).append(" hours\n");
                    vehicleDetails.append("  Fee: ").append(foundVehicle.fee).append("\n\n");
                    JOptionPane.showMessageDialog(frame, vehicleDetails.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Vehicle not found!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid vehicle number!");
            }
        });

        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            main(null);
        });
    }

    private static void showUserPanel(JFrame frame) {
        frame.getContentPane().removeAll();

        JPanel userPanel = new JPanel(new GridLayout(0, 2));

        JLabel ownerNameLabel = new JLabel("Owner Name:");
        JTextField ownerNameField = new JTextField();

        JLabel mobileLabel = new JLabel("Mobile Number:");
        JTextField mobileField = new JTextField();

        JLabel vehicleNumberLabel = new JLabel("Vehicle Number:");
        JTextField vehicleNumberField = new JTextField();

        JLabel vehicleTypeLabel = new JLabel("Vehicle Type (Car/Bike):");
        JTextField vehicleTypeField = new JTextField();

        JLabel parkingDateLabel = new JLabel("Parking Date (YYYY-MM-DD):");
        JTextField parkingDateField = new JTextField();

        JLabel durationLabel = new JLabel("Duration (hours):");
        JTextField durationField = new JTextField();

        JLabel slotLabel = new JLabel("Select Slot:");
        String[] slotOptions = new String[50];
        for (int i = 0; i < 50; i++) {
            slotOptions[i] = String.valueOf(i + 1);
        }
        JComboBox<String> slotComboBox = new JComboBox<>(slotOptions);

        JButton parkButton = new JButton("Park Vehicle");
        JButton unparkButton = new JButton("Unpark Vehicle");
        JButton addDurationButton = new JButton("Add Duration");
        JButton bookedSlotsButton = new JButton("Booked Slots");
        JLabel slotsLabel = new JLabel("Available Slots: " + availableSlots);

        JButton backButton = new JButton("Back");

        userPanel.add(ownerNameLabel);
        userPanel.add(ownerNameField);
        userPanel.add(mobileLabel);
        userPanel.add(mobileField);
        userPanel.add(vehicleNumberLabel);
        userPanel.add(vehicleNumberField);
        userPanel.add(vehicleTypeLabel);
        userPanel.add(vehicleTypeField);
        userPanel.add(parkingDateLabel);
        userPanel.add(parkingDateField);
        userPanel.add(durationLabel);
        userPanel.add(durationField);
        userPanel.add(slotLabel);
        userPanel.add(slotComboBox);
        userPanel.add(parkButton);
        userPanel.add(unparkButton);
        userPanel.add(addDurationButton);
        userPanel.add(bookedSlotsButton);
        userPanel.add(slotsLabel);
        userPanel.add(backButton);
        frame.add(userPanel);
        frame.revalidate();
        frame.repaint();

        parkButton.addActionListener(e -> {
            String ownerName = ownerNameField.getText();
            String mobile = mobileField.getText();
            String vehicleNumber = vehicleNumberField.getText();
            String vehicleType = vehicleTypeField.getText();
            String parkingDate = parkingDateField.getText();
            try {
                int duration = Integer.parseInt(durationField.getText());
                int selectedSlot = Integer.parseInt((String) slotComboBox.getSelectedItem());

                if (slots.containsKey(selectedSlot)) {
                    JOptionPane.showMessageDialog(frame, "Slot " + selectedSlot + " is already booked!");
                } else {
                    double fee = calculateFee(vehicleType, duration);

                    if (availableSlots > 0) {
                        parkedVehicles.add(new Vehicle(ownerName, mobile, vehicleNumber, vehicleType, parkingDate, duration, fee));
                        slots.put(selectedSlot, new Vehicle(ownerName, mobile, vehicleNumber, vehicleType, parkingDate, duration, fee));
                        availableSlots--;
                        slotsLabel.setText("Available Slots: " + availableSlots);
                        monthlyIncome += fee;
                        JOptionPane.showMessageDialog(frame, "Vehicle Parked Successfully! Fee: " + fee);
                        ownerNameField.setText("");
                        mobileField.setText("");
                        vehicleNumberField.setText("");
                        vehicleTypeField.setText("");
                        parkingDateField.setText("");
                        durationField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No available slots!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid duration! Please enter a number.");
            }
        });

        unparkButton.addActionListener(e -> {
            String vehicleNumber = JOptionPane.showInputDialog(frame, "Enter Vehicle Number:");
            if (vehicleNumber != null && !vehicleNumber.trim().isEmpty()) {
                Vehicle foundVehicle = null;
                for (Vehicle v : parkedVehicles) {
                    if (v.vehicleNumber.equalsIgnoreCase(vehicleNumber.trim())) {
                        foundVehicle = v;
                        break;
                    }
                }
                if (foundVehicle != null) {
                    for (int slot : slots.keySet()) {
                        if (slots.get(slot).vehicleNumber.equals(vehicleNumber)) {
                            slots.remove(slot);
                            break;
                        }
                    }
                    parkedVehicles.remove(foundVehicle);
                    availableSlots++;
                    slotsLabel.setText("Available Slots: " + availableSlots);
                    JOptionPane.showMessageDialog(frame, "Vehicle Unparked Successfully! Fee: " + foundVehicle.fee);
                } else {
                    JOptionPane.showMessageDialog(frame, "Vehicle not found!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid vehicle number!");
            }
        });

        addDurationButton.addActionListener(e -> {
            String vehicleNumber = JOptionPane.showInputDialog(frame, "Enter Vehicle Number:");
            if (vehicleNumber != null && !vehicleNumber.trim().isEmpty()) {
                Vehicle foundVehicle = null;
                for (Vehicle v : parkedVehicles) {
                    if (v.vehicleNumber.equalsIgnoreCase(vehicleNumber.trim())) {
                        foundVehicle = v;
                        break;
                    }
                }
                if (foundVehicle != null) {
                    for (int slot : slots.keySet()) {
                        if (slots.get(slot).vehicleNumber.equals(vehicleNumber)) {
                            String additionalDurationStr = JOptionPane.showInputDialog(frame, "Enter Additional Duration (hours):");
                            try {
                                int additionalDuration = Integer.parseInt(additionalDurationStr);
                                foundVehicle.duration += additionalDuration;
                                double additionalFee = calculateAdditionalFee(foundVehicle.vehicleType, additionalDuration);
                                foundVehicle.fee += additionalFee;
                                monthlyIncome += additionalFee;
                                slots.get(slot).duration += additionalDuration;
                                slots.get(slot).fee += additionalFee;
                                JOptionPane.showMessageDialog(frame, "Duration and Fee Updated Successfully! Additional Fee: " + additionalFee);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Invalid duration! Please enter a number.");
                            }
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Vehicle not found!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid vehicle number!");
            }
        });

        bookedSlotsButton.addActionListener(_ -> {
            StringBuilder bookedSlots = new StringBuilder("Booked Slots:\n\n");
            for (int slot : slots.keySet()) {
                bookedSlots.append("Slot ").append(slot).append(": ").append(slots.get(slot).vehicleNumber).append("\n");
            }
            if (slots.isEmpty()) {
                bookedSlots.append("No slots are currently booked.");
            }
            JOptionPane.showMessageDialog(frame, bookedSlots.toString());
        });

        backButton.addActionListener(_ -> {
            frame.getContentPane().removeAll();
            main(null);
        });
    }

    private static double calculateFee(String vehicleType, int duration) {
        double rate = vehicleType.equalsIgnoreCase("Car") ? 10.0 : 5.0;
        return rate * duration;
    }

    private static double calculateAdditionalFee(String vehicleType, int additionalDuration) {
        double rate = vehicleType.equalsIgnoreCase("Car") ? 5.0 : 2.0;
        return rate * additionalDuration;
    };
}
