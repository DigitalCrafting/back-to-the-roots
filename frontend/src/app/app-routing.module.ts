import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EregoldRoutes} from "./utils/routes.enum";

const routes: Routes = [
    {path: EregoldRoutes.LOGIN, loadChildren: () => import('./modules/login/login.module').then(m => m.LoginModule)},
    {
        path: EregoldRoutes.REGISTER,
        loadChildren: () => import('./modules/register/register.module').then(m => m.RegisterModule)
    },
    {
        path: EregoldRoutes.UI,
        loadChildren: () => import('./modules/dashboard/dashboard.module').then(m => m.DashboardModule)
    },
    {path: '**', redirectTo: 'login'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
