package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Room;
import com.gestionSalleCefim.Group3.repositories.RoomRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
public class RoomController extends BaseController<Room, RoomRepository>{
}
