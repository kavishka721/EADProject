using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
    public class Fuel
    {
        public int FuelId { get; set; }
        public string Type { get; set; }
        public int Quantity { get; set; }
        public bool Status { get; set; }
        public int Queue { get; set; }
    }
}
