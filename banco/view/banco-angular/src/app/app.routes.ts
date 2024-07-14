import { Routes } from '@angular/router';
import { SidebarComponent } from './component/sidebar/sidebar.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'sign', component: SignupComponent},
    {path: 'home', component: HomeComponent},
    {path: 'side', component: SidebarComponent},

    
];
