using System;
using System.Collections.Generic;

namespace InstagramServer.Models
{
    public partial class UserData
    {
        public Guid UserId { get; set; }
        public string Firstname { get; set; }
        public string Lastname { get; set; }
        public int? Age { get; set; }
        public string Description { get; set; }
        public string City { get; set; }
        public int Followers { get; set; }
        public int Follows { get; set; }
        public int IdData { get; set; }

        public virtual User User { get; set; }
    }
}
