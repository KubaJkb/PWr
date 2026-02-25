using Microsoft.EntityFrameworkCore;
using WebStore.Data;

namespace WebStore
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.
            builder.Services.AddRazorPages();

            builder.Services.AddDbContextPool<StoreDbContext>(options => 
                options.UseSqlServer(builder.Configuration.GetConnectionString("StoreDb")));

            var app = builder.Build();

            var cultureInfo = new System.Globalization.CultureInfo("pl-PL");
            cultureInfo.NumberFormat.NumberDecimalSeparator = ",";
            cultureInfo.NumberFormat.CurrencyDecimalSeparator = ",";

            var localizationOptions = new RequestLocalizationOptions
            {
                DefaultRequestCulture = new Microsoft.AspNetCore.Localization.RequestCulture(cultureInfo),
                SupportedCultures = new List<System.Globalization.CultureInfo> { cultureInfo },
                SupportedUICultures = new List<System.Globalization.CultureInfo> { cultureInfo }
            };

            if (!app.Environment.IsDevelopment())
            {
                app.UseExceptionHandler("/Error");
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseRouting();
            app.UseAuthorization();

            app.MapRazorPages();

            app.Run();
        }
    }
}
