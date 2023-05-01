import {ComponentFixture, TestBed} from '@angular/core/testing';

import {RentPageComponent} from './rent-page.component';

describe('RentPageComponent', () => {
    let component: RentPageComponent;
    let fixture: ComponentFixture<RentPageComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [RentPageComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(RentPageComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
