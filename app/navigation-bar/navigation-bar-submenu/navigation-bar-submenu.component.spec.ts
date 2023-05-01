import {ComponentFixture, TestBed} from '@angular/core/testing';

import {NavigationBarSubmenuComponent} from './navigation-bar-submenu.component';

describe('NavigationBarSubmenuComponent', () => {
    let component: NavigationBarSubmenuComponent;
    let fixture: ComponentFixture<NavigationBarSubmenuComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [NavigationBarSubmenuComponent]
        })
            .compileComponents();

        fixture = TestBed.createComponent(NavigationBarSubmenuComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
