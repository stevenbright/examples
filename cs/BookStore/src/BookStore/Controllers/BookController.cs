using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;

namespace BookStore.Controllers
{
    [Route("api/book")]
    public class BookController
    {
        [HttpGet]
        public IEnumerable<Book> Get()
        {
            return new Book[]
            {
                new Book() { Id = "1", Name = "Far Rainbow", Created = DateTime.Today, ReferralCount = 347 },
                new Book() { Id = "2", Name = "Hard to be a God", Created = DateTime.Today, ReferralCount = 905 },
                new Book() { Id = "3", Name = "Ugly Swans", Created = DateTime.Today, ReferralCount = 561 }
            };
        }
    }
}
