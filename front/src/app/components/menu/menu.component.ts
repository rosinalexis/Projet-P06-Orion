import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  isUser = false;

  constructor() {
  }

  ngOnInit(): void {
    this.isUser = localStorage.getItem('token') ? true : false;
  }


}
