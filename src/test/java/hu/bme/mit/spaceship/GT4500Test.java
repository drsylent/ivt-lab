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
    verify(mockTSPrimary, times(1)).fire(1);
    verifyZeroInteractions(mockTSSecondary);
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
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }
  
  /**
   * Test #1: firing a single torpedo, but both fails
   */
  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
	when(mockTSPrimary.fire(1)).thenReturn(false);
	when(mockTSSecondary.fire(1)).thenReturn(false);
	when(mockTSPrimary.isEmpty()).thenReturn(false);
	when(mockTSSecondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
    assertEquals(false, result);
  }
  
  /**
   * Test #2: firing 2 torpedoes - both stores will fire
   */
  @Test
  public void fireTorpedo_Single_Alternates(){
    // Arrange
	when(mockTSPrimary.fire(1)).thenReturn(true);
	when(mockTSSecondary.fire(1)).thenReturn(true);
	when(mockTSPrimary.isEmpty()).thenReturn(false);
	when(mockTSSecondary.isEmpty()).thenReturn(false);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
    assertEquals(true, result1);
    assertEquals(true, result2);
  }
  
  /**
   * Test #3: firing 2 torpedoes - but the secondary is empty
   */
  @Test
  public void fireTorpedo_Single_Secondary_Empty(){
    // Arrange
	when(mockTSPrimary.fire(1)).thenReturn(true);
	when(mockTSSecondary.fire(1)).thenReturn(false);
	when(mockTSPrimary.isEmpty()).thenReturn(false);
	when(mockTSSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTSPrimary, times(2)).fire(1);
    verify(mockTSSecondary, times(0)).fire(1);
    assertEquals(true, result1);
    assertEquals(true, result2);
  }
  
  /**
   * Test #4: firing from both stores - only the secondary succeeds
   */
  @Test
  public void fireTorpedo_All_Primary_Fails(){
    // Arrange
	when(mockTSPrimary.fire(1)).thenReturn(false);
	when(mockTSSecondary.fire(1)).thenReturn(true);
	when(mockTSPrimary.isEmpty()).thenReturn(false);
	when(mockTSSecondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTSPrimary, times(1)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }
  
  /**
   * Test #5: firing from both stores - both are empty
   */
  @Test
  public void fireTorpedo_All_Empty(){
    // Arrange
	when(mockTSPrimary.fire(1)).thenReturn(false);
	when(mockTSSecondary.fire(1)).thenReturn(false);
	when(mockTSPrimary.isEmpty()).thenReturn(true);
	when(mockTSSecondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTSPrimary, times(0)).fire(1);
    verify(mockTSSecondary, times(0)).fire(1);
    assertEquals(false, result);
  }
  
  /**
   * From source code
   * It's hard to follow, what the test actually analyses
   * Test #6: try to fire primary, but it is empty,
   * 			so fire secondary
   */
  @Test
  public void fireTorpedo_Single_Primary_Empty(){
    // Arrange
	when(mockTSPrimary.fire(1)).thenReturn(false);
	when(mockTSSecondary.fire(1)).thenReturn(true);
	when(mockTSPrimary.isEmpty()).thenReturn(true);
	when(mockTSSecondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTSPrimary, times(0)).fire(1);
    verify(mockTSSecondary, times(1)).fire(1);
    assertEquals(true, result);
  }

}
