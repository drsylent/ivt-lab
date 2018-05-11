package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private TorpedoStore mockTSPrimary;
  private TorpedoStore mockTSSecondary;
  private GT4500 ship;

  @Before
  public void init(){
	mockTSPrimary = mock(TorpedoStore.class);
	mockTSSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTSPrimary, mockTSSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
	when(mockTSPrimary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
	when(mockTSPrimary.fire(1)).thenReturn(true);
	when(mockTSSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

}
