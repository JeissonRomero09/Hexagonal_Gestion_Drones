package co.edu.poli.sw2.Aplicacion.mediator.components;

/** Verifica que la batería disponible cubra el mínimo requerido. */
public class BatterySystem {
    public boolean hasSufficientEnergy(int availablePercentage, int requiredPercentage) {
        return availablePercentage >= requiredPercentage;
    }
}
