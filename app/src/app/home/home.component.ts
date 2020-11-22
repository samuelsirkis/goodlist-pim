import { Component, OnInit } from '@angular/core';

import { SwiperOptions } from 'swiper';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor() {}

  config: SwiperOptions = {
    autoplay: true,
    // initialSlide: 1,
    // slidesPerView: 3,
    pagination: { el: '.swiper-pagination', clickable: true },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    spaceBetween: 0,
  };

  ngOnInit(): void {}
}
