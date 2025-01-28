// RobotBuilder Version: 6.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Elevator extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private TalonFX stage1motor;
    private TalonFX stage2motor;
    private DigitalInput elevatorBottomSwich;
    private DigitalInput elevatorTopSwich;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public boolean enabledClimb = false;
    /* Start at position 0, use slot 0 */
    private final PositionVoltage m_positionVoltage = new PositionVoltage(0).withSlot(0);
    /* Start at position 0, use slot 1 */
    private final NeutralOut m_brake = new NeutralOut();
    private final PositionTorqueCurrentFOC m_positionTorque = new PositionTorqueCurrentFOC(0).withSlot(1);
    private final XboxController accessory = new XboxController(0);
    /**
    *
    */
    public Elevator() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        stage1motor = new TalonFX(14);
        stage2motor = new TalonFX(15);
        elevatorBottomSwich = new DigitalInput(1);
        addChild("elevatorBottomSwich", elevatorBottomSwich);

        elevatorTopSwich = new DigitalInput(0);
        addChild("elevatorTopSwich", elevatorTopSwich);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // Voltage
        TalonFXConfiguration configs = new TalonFXConfiguration();
        configs.Slot0.kP = Constants.PID.P; // An error of 1 rotation results in 2.4 V output
        configs.Slot0.kI = Constants.PID.I; // No output for integrated error
        configs.Slot0.kD = Constants.PID.D; // A velocity of 1 rps results in 0.1 V output
        // Peak output of 8 V
        configs.Voltage.withPeakForwardVoltage(Volts.of(8))
                .withPeakReverseVoltage(Volts.of(-8));

        // Config Saving
        /* Retry config apply up to 5 times, report if failure */
        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = stage1motor.getConfigurator().apply(configs);
            if (status.isOK())
                break;
        }
        if (!status.isOK()) {
            System.out.println("Could not apply configs, error code: " + status.toString());
        }
        for (int i = 0; i < 5; ++i) {
            status = stage1motor.getConfigurator().apply(configs);
            if (status.isOK())
                break;
        }
        if (!status.isOK()) {
            System.out.println("Could not apply configs, error code: " + status.toString());
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Bottom Vol", stage1motor.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Bottom Pos", stage1motor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("Top Vol", stage2motor.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Top Pos", stage2motor.getPosition().getValueAsDouble());
        // SmartDashboard.putBoolean("Climbing", enabledClimb);

        if (elevatorBottomSwich.get()) {
            stage1motor.setPosition(0);
        }
        if (elevatorTopSwich.get()) {
            stage2motor.setPosition(0);
        }

        SmartDashboard.putBoolean("bottomSwitch", getBottomSwitch());
        SmartDashboard.putBoolean("topSwitch", getTopSwitch());

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public boolean getBottomSwitch() {
        return elevatorBottomSwich.get();
    }

    public boolean getTopSwitch() {
        return elevatorTopSwich.get();
    }

    public void setFirst() {
        stage1motor.setControl(m_positionVoltage.withPosition(Constants.Positions.lowerFirst));
        stage2motor.setControl(m_positionVoltage.withPosition(Constants.Positions.upperFirst));
    }

    public void setSecond() {
        stage1motor.setControl(m_positionVoltage.withPosition(Constants.Positions.lowerSecond));
        stage2motor.setControl(m_positionVoltage.withPosition(Constants.Positions.upperSecond));

    }

    public void setThird() {
        stage1motor.setControl(m_positionVoltage.withPosition(Constants.Positions.lowerThird));
        stage2motor.setControl(m_positionVoltage.withPosition(Constants.Positions.upperThird));

    }

    public void setFourth() {
        stage1motor.setControl(m_positionVoltage.withPosition(Constants.Positions.lowerFourth));
        stage2motor.setControl(m_positionVoltage.withPosition(Constants.Positions.upperFourth));

    }

    public void setClimb() {
        if (accessory.getRightTriggerAxis() >= .5) {
            stage1motor.setControl(m_positionVoltage.withPosition(Constants.Positions.lowerClimb));
            stage2motor.setControl(m_positionVoltage.withPosition(Constants.Positions.upperClimb));
            // enabledClimb = true;
        }
    }
}
