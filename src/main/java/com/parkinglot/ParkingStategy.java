package com.parkinglot;

import java.util.List;

public interface ParkingStategy {
    ParkingLot selectParkingLot(List<ParkingLot> parkingLots);

}
