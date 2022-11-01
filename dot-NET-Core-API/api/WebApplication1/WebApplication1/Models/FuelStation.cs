using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Models
{
    public class FuelStation
    {
        public ObjectId Id { get; set; }
        public int StationId { get; set; }
        public string City { get; set; }
        public string StationName { get; set; }
        public bool Status { get; set; }
        public int Quantity { get; set; }
        public int Queue { get; set; }
        //public string fuel new [public string type { get; set; }, public string quantity { get; set; }]

        
    }
}
